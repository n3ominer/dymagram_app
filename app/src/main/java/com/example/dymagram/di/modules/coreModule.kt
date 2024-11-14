package com.example.dymagram.di.modules

import com.example.dymagram.repositories.DirectMessagesRepository
import com.example.dymagram.repositories.GlobalDataRepository
import com.example.dymagram.viewmodel.DirectMessagesViewModel
import com.example.dymagram.viewmodel.HomeFeedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val coreModule = module {

    /*
        single {  } //Injecter la dépendance sous forme de singleton


        factory {  } // Injecter une nouvelle dépendance à chaque fois qu'on en a besoin

        viewModel {  }
     */

    // Gestion de dépendances pour le fragment Feed
    viewModel { HomeFeedViewModel(get()) }

    single { GlobalDataRepository(get()) }


    // Gestion de dépendances pour le fragment Message
    viewModel { DirectMessagesViewModel(get()) }

    single { DirectMessagesRepository(get()) }
}