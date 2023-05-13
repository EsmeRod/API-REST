package sv.edu.udb.api_rest.Vista


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sv.edu.udb.api_rest.Controlador.Profesor
import sv.edu.udb.api_rest.R

class Activity_Profesor_Item(private val profes: List<Profesor>) :
    RecyclerView.Adapter<Activity_Profesor_Item.ViewHolder>() {
    private var onItemClick: OnItemClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(R.id.tvNombre_prof)
        val apellidoTextView: TextView = view.findViewById(R.id.tvApellido_prof)
        val edadTextView: TextView = view.findViewById(R.id.tvEdad_prof)
        val materiaTextView: TextView = view.findViewById(R.id.tvMateria_prof)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profesor_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profesor = profes[position]
        holder.nombreTextView.text = profesor.nombre
        holder.apellidoTextView.text = profesor.apellido
        holder.edadTextView.text = profesor.edad.toString()
        holder.materiaTextView.text = profesor.materia

        holder.itemView.setOnClickListener {
            onItemClick?.onItemClick(profesor)
        }
    }
    override fun getItemCount(): Int {
        return profes.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClick = listener
    }

    interface OnItemClickListener {
        fun onItemClick(profesor: Profesor)
    }
}