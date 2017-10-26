package mustafaozhan.github.com.mycurrencies.ui.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import mustafaozhan.github.com.mycurrencies.R
import mustafaozhan.github.com.mycurrencies.model.data.Setting
import ninja.sakib.pultusorm.callbacks.Callback
import ninja.sakib.pultusorm.core.*
import ninja.sakib.pultusorm.exceptions.PultusORMException
import org.jetbrains.anko.doAsync

/**
 * Created by Mustafa Ozhan on 10/9/17 at 12:57 PM on Arch Linux.
 */
class SettingsAdapter(private val settingsList: ArrayList<Setting>?) : RecyclerView.Adapter<SettingsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.textView)
        var checkBox: CheckBox = view.findViewById(R.id.checkBox)
        var constraintRow:ConstraintLayout=view.findViewById(R.id.constraintRow)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_settings, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val setting = settingsList!![position]
        holder.setIsRecyclable(false)
        holder.name.text = setting.name
        holder.checkBox.isChecked = setting.isActive == "true"

        holder.constraintRow.setOnClickListener{
            holder.checkBox.isChecked=!holder.checkBox.isChecked
        }
        val myDatabase = PultusORM("myDatabase.db", holder.itemView.context.filesDir.absolutePath)

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            setting.isActive = isChecked.toString()

            doAsync {
                val value = isChecked.toString()

                val condition: PultusORMCondition = PultusORMCondition.Builder()
                        .eq("name", setting.name.toString())
                        .build()
                val updater: PultusORMUpdater = PultusORMUpdater.Builder()
                        .set("isActive", value)
                        .condition(condition)   // condition is optional
                        .build()

                class ResponseCallback : Callback {
                    override fun onSuccess(type: PultusORMQuery.Type) {
                        log(type.name, "Success")
                    }

                    override fun onFailure(type: PultusORMQuery.Type, exception: PultusORMException) {
                        log(type.name, "Failure")
                        exception.printStackTrace()
                    }
                }
                myDatabase.update(Setting(), updater, ResponseCallback())

            }
        }


    }

    override fun getItemCount(): Int = settingsList?.size ?: -1
}
