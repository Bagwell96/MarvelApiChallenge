package com.mertkavrayici.marvelkarakterlerideneme

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mertkavrayici.marvelkarakterlerideneme.API.RetrofitInit
import com.mertkavrayici.marvelkarakterlerideneme.Models.CharacterReturn
import com.mertkavrayici.marvelkarakterlerideneme.Models.Hero
import com.mertkavrayici.marvelkarakterlerideneme.Utils.API_PUBLIC_KEY
import com.mertkavrayici.marvelkarakterlerideneme.Utils.CHARACTER_LIMIT
import com.mertkavrayici.marvelkarakterlerideneme.Utils.getHash
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeroesViewModel:ViewModel() {
    val herosLiveData: MutableLiveData<List<Hero>> = MutableLiveData()


    fun getHeros(offset:Int){

        RetrofitInit.service.getAllEvents(CHARACTER_LIMIT, offset, ts, API_PUBLIC_KEY, getHash(ts.toString())).enqueue(object:
            Callback<CharacterReturn> {

            override fun onFailure(call: Call<CharacterReturn>, t: Throwable) {


            }
            override fun onResponse(
                call: Call<CharacterReturn>,
                response: Response<CharacterReturn>
            ) {

                if(response.isSuccessful){

                    val heros = response.body()?.data?.results ?: emptyList()
                    herosLiveData.value = heros
                }else{


                }
            }
        })
    }

    companion object{
        val ts = System.currentTimeMillis()/1000
    }
}