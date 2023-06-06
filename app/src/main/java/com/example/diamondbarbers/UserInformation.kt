package com.example.diamondbarbers

import com.example.diamondbarbers.models.Barbershop
import com.example.diamondbarbers.models.HairStylist
import com.example.diamondbarbers.models.User

object UserInformation {
    var userInfo: User? = null

    var currentBarberShop: Barbershop? = null
    var currentHairStylist: HairStylist? = null
}