package app.te.alo_chef.domain.utils

import android.content.Context
import android.view.View
import androidx.appcompat.widget.PopupMenu
import app.te.alo_chef.domain.general.entity.countries.CityModel
import app.te.alo_chef.domain.general.entity.countries.RegionsItem

fun showCityPopUp(context: Context, view: View, types: List<CityModel>): PopupMenu {
    val typesPopUps = PopupMenu(context, view)
    for (i in types.indices) {
        typesPopUps.menu.add(i, i, i, types[i].name)
    }
    typesPopUps.show()
    return typesPopUps
}

fun showRegionsPopUp(context: Context, view: View, types: List<RegionsItem>): PopupMenu {
    val typesPopUps = PopupMenu(context, view)
    for (i in types.indices) {
        typesPopUps.menu.add(i, i, i, types[i].name)
    }
    typesPopUps.show()
    return typesPopUps
}
