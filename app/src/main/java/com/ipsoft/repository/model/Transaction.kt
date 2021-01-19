package com.ipsoft.repository.model

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    Ph
 *  Date:       18/01/2021
 */


data class Transaction(val amount: Double,
                       val id : String,
                       val authentication : String,
                       val tType:String,
                       val createdAd:String,
                       val receiver : String,
                       val description : String
)