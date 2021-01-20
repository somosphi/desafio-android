package com.ipsoft.ph.util

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       20/01/2021
 */

class DateUtils {

    companion object {
        fun convertDate(date: String) : String {

            val fDate = date.split("-").toTypedArray()

            return "${fDate[1]}/${fDate[0]}"

        }
    }
}