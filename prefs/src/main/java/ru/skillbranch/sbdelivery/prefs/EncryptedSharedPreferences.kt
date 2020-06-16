package ru.skillbranch.sbdelivery.prefs

import android.content.SharedPreferences
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.IllegalStateException
import java.lang.UnsupportedOperationException
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class EncryptedSharedPreferences(private val prefs: SharedPreferences) : SharedPreferences {

    override fun contains(key: String?): Boolean {
        return prefs.contains(key)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return decrypt(key, defValue) { it.readBoolean() }
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    override fun getInt(key: String, defValue: Int): Int {
        return decrypt(key, defValue) { it.readInt() }
    }

    override fun getAll(): MutableMap<String, *> {
        throw UnsupportedOperationException("Operation not yet implemented")
    }

    override fun edit(): SharedPreferences.Editor {
        return Editor(prefs, prefs.edit())
    }

    override fun getLong(key: String, defValue: Long): Long {
        return decrypt(key, defValue) { it.readLong() }
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return decrypt(key, defValue) { it.readFloat() }
    }

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String> {
        throw UnsupportedOperationException("Operation not yet implemented")
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun getString(key: String, defValue: String?): String? {
        return decrypt(key, defValue) { it.readUTF() }
    }

    private fun <T> decrypt(key: String, defValue: T, function: (DataInputStream) -> T): T {
        val encrypted = prefs.getString(key, null) ?: return defValue

        val data = decrypt(prefs, encrypted)

        DataInputStream(ByteArrayInputStream(data)).use {
            return function.invoke(it)
        }
    }

    class Editor(
        private val prefs: SharedPreferences,
        private val editor: SharedPreferences.Editor
    ) : SharedPreferences.Editor {

        override fun clear() = apply {
            editor.clear()
        }

        override fun putLong(key: String, value: Long) = apply {
            putEncrypted(key) { it.writeLong(value) }
        }

        override fun putInt(key: String, value: Int) = apply {
            putEncrypted(key) { it.writeInt(value) }
        }

        override fun remove(key: String?) = apply {
            editor.remove(key)
        }

        override fun putBoolean(key: String, value: Boolean) = apply {
            putEncrypted(key) { it.writeBoolean(value) }
        }

        override fun putStringSet(
            key: String?,
            values: MutableSet<String>?
        ): SharedPreferences.Editor {
            throw UnsupportedOperationException("Method not supported")
        }

        override fun commit(): Boolean {
            return editor.commit()
        }

        override fun putFloat(key: String, value: Float) = apply {
            putEncrypted(key) { it.writeFloat(value) }
        }

        override fun apply() {
            editor.apply()
        }

        override fun putString(key: String, value: String?) = apply {
            if (value == null) {
                editor.remove(key)
            } else {
                putEncrypted(key) { it.writeUTF(value) }
            }
        }

        private fun putEncrypted(key: String, function: (DataOutputStream) -> Unit) {
            val baos = ByteArrayOutputStream()
            DataOutputStream(baos).use {
                function.invoke(it)
            }
            val data = baos.toByteArray()

            val encrypted = encrypt(prefs, data)
            editor.putString(key, encrypted)
        }

    }

    companion object {

        private val MASTER_DEFENCE_PASS = "BIXHP3HA1FGXVEP48ZHRVVOXMSTMXPUAPHO3TVZ5LHU=".toCharArray()
        private val lock = Any()
        private var random = SecureRandom()

        fun encrypt(prefs: SharedPreferences, plain: ByteArray): String {
            synchronized(lock) {
                var masterKey = decryptMasterKey(prefs)
                if (masterKey == null) {
                    masterKey = encryptMasterKey(prefs)
                }

                val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                val salt = random(24)
                val spec = PBEKeySpec(masterKey.toCharArray(), salt, 65536, 256)
                val tmp = factory.generateSecret(spec)
                val secretKey = SecretKeySpec(tmp.encoded, "AES")

                val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
                val iv = random(16)
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
                val encrypted = cipher.doFinal(plain)

                val fullText = ByteArrayOutputStream().apply {
                    DataOutputStream(this).use {
                        it.write(salt)
                        it.write(iv)
                        it.writeInt(encrypted.size)
                        it.write(encrypted)
                    }
                }.toByteArray()
                return Base64.encodeToString(fullText, Base64.DEFAULT)
            }
        }

        fun decrypt(prefs: SharedPreferences, encrypted: String): ByteArray {
            synchronized(lock) {
                val masterKey = decryptMasterKey(prefs) ?: throw IllegalStateException("Unable to find master key")

                val fullText = Base64.decode(encrypted, Base64.DEFAULT)
                val salt = ByteArray(24)
                val iv = ByteArray(16)
                var encrypted = ByteArray(0)
                DataInputStream(ByteArrayInputStream(fullText)).use {
                    it.read(salt)
                    it.read(iv)
                    val encryptedSize = it.readInt()
                    encrypted = ByteArray(encryptedSize)
                    it.read(encrypted)
                }

                val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                val spec = PBEKeySpec(masterKey.toCharArray(), salt, 65536, 256)
                val tmp = factory.generateSecret(spec)
                val secretKey = SecretKeySpec(tmp.encoded, "AES")

                val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
                cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
                return cipher.doFinal(encrypted)
            }
        }

        private fun encryptMasterKey(prefs: SharedPreferences): String {
            val masterKey = random(32)

            val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val salt = random(24)
            val spec = PBEKeySpec(MASTER_DEFENCE_PASS, salt, 65536, 256)
            val tmp = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.encoded, "AES")

            val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val iv = random(16)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
            val encryptedKey = cipher.doFinal(masterKey)

            val fullText = ByteArrayOutputStream().apply {
                DataOutputStream(this).use {
                    it.write(salt)
                    it.write(iv)
                    it.writeInt(encryptedKey.size)
                    it.write(encryptedKey)
                }
            }.toByteArray()

            val encryptedText = Base64.encodeToString(fullText, Base64.DEFAULT)
            prefs.edit().putString("!!|___MASTER_KEY___|!!", encryptedText).apply()
            return Base64.encodeToString(masterKey, Base64.DEFAULT)
        }

        private fun decryptMasterKey(prefs: SharedPreferences): String? {
            val encryptedText = prefs.getString("!!|___MASTER_KEY___|!!", null) ?: return null

            val fullText = Base64.decode(encryptedText, Base64.DEFAULT)

            val salt = ByteArray(24)
            val iv = ByteArray(16)
            var encryptedKey = ByteArray(0)
            DataInputStream(ByteArrayInputStream(fullText)).use {
                it.read(salt)
                it.read(iv)
                val size = it.readInt()
                encryptedKey = ByteArray(size)
                it.read(encryptedKey)
            }

            val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val spec = PBEKeySpec(MASTER_DEFENCE_PASS, salt, 65536, 256)
            val tmp = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.encoded, "AES")

            val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
            val masterKey = cipher.doFinal(encryptedKey)
            return Base64.encodeToString(masterKey, Base64.DEFAULT)
        }

        private fun random(size: Int) : ByteArray {
            val randomBytes = ByteArray(size)
            random.nextBytes(randomBytes)
            return randomBytes
        }

    }

}