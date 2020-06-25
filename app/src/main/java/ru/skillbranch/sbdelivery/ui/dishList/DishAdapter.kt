package ru.skillbranch.sbdelivery.ui.dishList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

class DishAdapter(
    private val addListener: (Dish) -> Unit,
    private val clickListener: (Dish) -> Unit
): RecyclerView.Adapter<DishViewHolder>() {

    var items: List<Dish> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_list_item_dish, parent, false)
        return DishViewHolder(view, addListener, clickListener)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}