package com.skillbox.skillbox.roomdao.fragments.tournaments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.skillbox.roomdao.database.connections.TournamentsWithClubs
import com.skillbox.skillbox.roomdao.database.entities.Clubs
import com.skillbox.skillbox.roomdao.database.entities.Tournaments
import com.skillbox.skillbox.roomdao.database.entities.TournamentsAndClubsCrossRef
import com.skillbox.skillbox.roomdao.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class TournamentDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val tournamentWithClubsLiveData = MutableLiveData<TournamentsWithClubs>()


    //    лайв дата статуса загрузки
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    private val gettingCrossTableForTournamentLiveData =
        MutableLiveData<TournamentsAndClubsCrossRef>()
    val gettingCrossTableForTournament: LiveData<TournamentsAndClubsCrossRef>
        get() = gettingCrossTableForTournamentLiveData

    private val getAllClubsLiveData = MutableLiveData<List<Clubs>>()
    val getAllClubs: LiveData<List<Clubs>>
        get() = getAllClubsLiveData

    private val deleteTournamentLiveData = MutableLiveData<Boolean>()
    val deleteTournamentLD: LiveData<Boolean>
        get() = deleteTournamentLiveData

    private val updateTournamentLiveData = MutableLiveData<Boolean>()
    val updateTournament: LiveData<Boolean>
        get() = updateTournamentLiveData

    //    лайв дата ошибок
    private val isErrorLiveData = SingleLiveEvent<String>()
    val isError: LiveData<String>
        get() = isErrorLiveData
    private val repo = TournamentDetailsRepository()

    fun getTournamentWithClubs(tournamentId: Long) {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                tournamentWithClubsLiveData.postValue(repo.getTournamentWithClubs(tournamentId))
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun getAllClubs() {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                getAllClubsLiveData.postValue(repo.getAllClubs())
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun updateTournament(
        tournament: Tournaments,
        tournamentsAndClubsCrossRef: TournamentsAndClubsCrossRef
    ) {
        isLoadingLiveData.postValue(true)
        updateTournamentLiveData.postValue(false)
        viewModelScope.launch {
            try {
                repo.updateTournament(tournament, tournamentsAndClubsCrossRef)
                updateTournamentLiveData.postValue(true)
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun deleteTournament(tournament: Tournaments) {
        isLoadingLiveData.postValue(true)
        deleteTournamentLiveData.postValue(false)
        viewModelScope.launch {
            try {
                repo.deleteTournament(tournament)
                deleteTournamentLiveData.postValue(true)
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun gettingCrossTableForTournament(tournamentId: Long) {
        isLoadingLiveData.postValue(true)
        viewModelScope.launch {
            try {
                gettingCrossTableForTournamentLiveData.postValue(
                    repo.gettingCrossTableForTournament(
                        tournamentId
                    )
                )
            } catch (t: Throwable) {
                isErrorLiveData.postValue(t.message)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}