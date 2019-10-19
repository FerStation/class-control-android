package br.com.fernandobrscunha.classcontrol

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.com.fernandobrscunha.classcontrol.models.School

class MyListAdpter (
    var mCtx: Context,
    var res: Int,
    var items: List<School>
): ArrayAdapter<School>(mCtx,res,items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //inflate layout
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(res, null)

        val textViewItemSchoolName: TextView = view.findViewById(R.id.textViewitemSchoolName)
        val textViewItemSchoolType: TextView = view.findViewById(R.id.textViewItemSchoolType)

        var schoolItems: School = items[position]

        textViewItemSchoolName.text = schoolItems.name
        textViewItemSchoolType.text = schoolItems.type

        return view
    }
}