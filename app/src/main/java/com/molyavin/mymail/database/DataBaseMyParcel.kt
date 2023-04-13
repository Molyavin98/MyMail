package com.molyavin.mymail.database

import android.util.Log
import com.google.firebase.database.*
import com.molyavin.mymail.creating_parcel.Adapter
import com.molyavin.mymail.databinding.ActivityMyParcelsBinding
import com.molyavin.mymail.utis.ParcelInfo

class DataBaseMyParcel(val binding: ActivityMyParcelsBinding) : DataBase() {

    override var dataBase: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("parcel").child(DataBaseAuth.phone)

    lateinit var adapter: Adapter
    var itemList = mutableListOf<ParcelInfo>()

    override fun saveDataBase() {}

    override fun readDataBase() {

        val eListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                itemList = mutableListOf<ParcelInfo>()

                val codes = dataSnapshot.children.map { it.key.toString() }
                ttnCount = codes.size

                for (i in codes) {

                    val parcelInfo: ParcelInfo? =
                        dataSnapshot.child(i).getValue(ParcelInfo::class.java)

                    if (parcelInfo != null) {
                        itemList.add(parcelInfo)
                    }
                }

                adapter = Adapter(itemList)
                binding.recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {}

        }

        dataBase.addValueEventListener(eListener)

    }

    companion object {
        @JvmStatic
        var ttnCount: Int = 0
    }
}
