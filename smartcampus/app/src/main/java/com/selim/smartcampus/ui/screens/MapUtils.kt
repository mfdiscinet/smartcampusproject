// MapUtils.kt: Harita ile ilgili yardımcı fonksiyonlar
// - formatTimeAgo: zaman damgasını "5 dakika önce" gibi string'e döndürür
// - colorForType: ReportType'a göre marker renk tonunu verir

package com.selim.smartcampus.ui.screens

import android.text.format.DateUtils
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.selim.smartcampus.data.ReportType

fun formatTimeAgo(timeMillis: Long): String {
    return DateUtils.getRelativeTimeSpanString(
        timeMillis,
        System.currentTimeMillis(),
        DateUtils.MINUTE_IN_MILLIS
    ).toString()
}

fun colorForType(type: ReportType): Float {
    return when (type) {
        ReportType.HEALTH -> BitmapDescriptorFactory.HUE_RED
        ReportType.SECURITY -> BitmapDescriptorFactory.HUE_ORANGE
        ReportType.ENVIRONMENT -> BitmapDescriptorFactory.HUE_GREEN
        ReportType.LOST_FOUND -> BitmapDescriptorFactory.HUE_AZURE
        ReportType.TECHNICAL -> BitmapDescriptorFactory.HUE_VIOLET
        ReportType.OTHER -> BitmapDescriptorFactory.HUE_YELLOW
    }
}
