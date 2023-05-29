package com.wdevs.simplethings.feature.thelist

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdevs.simplethings.R
import com.wdevs.simplethings.core.data.quotes.QuotesRepository
import com.wdevs.simplethings.core.data.quotes.QuotesRepositoryImpl
import com.wdevs.simplethings.core.model.QuoteResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LikeableQuoteResource(
    val quoteResource: QuoteResource,
    val isLiked : Boolean
)

sealed interface TheListUiState {
    object Loading : TheListUiState
    data class Success(val quotesList: List<QuoteResource>) : TheListUiState
}

@HiltViewModel
class TheListViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {
    companion object {
        private const val TAG = "TheListViewModel"
    }

    val uiState: StateFlow<TheListUiState> =
        (quotesRepository as QuotesRepositoryImpl).remoteQuotesStream.map { quotes ->
            TheListUiState.Success(quotes)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = TheListUiState.Loading
        )

    fun onQuoteLike(quoteResource: QuoteResource) {
        viewModelScope.launch {
            val hitSign: Int = 1

            // to be changed to get post method
            quotesRepository.updateQuote(
                quoteResource.copy(
                    hits = quoteResource.hits + hitSign * 1,
                )
            )
        }
    }

    fun postQuote(quoteResource: QuoteResource) {
        viewModelScope.launch {
            quotesRepository.postQuote(quoteResource)
        }
    }

    fun onQuoteDraggedToBottom(quoteResource: QuoteResource, context: Context) {
        viewModelScope.launch {
            Log.d(TAG, "onQuoteDraggedToBottom: save quote")
            quotesRepository.saveQuoteLocal(quoteResource)
        }
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, NotificationCompat.Builder(context, "simpleThingsChannel")
                .setSmallIcon(R.drawable.baseline_lightbulb_24)
                .setContentTitle("Author " + quoteResource.author)
                .setContentText(quoteResource.quote.substring(0, 10) + "...").setPriority(NotificationCompat.PRIORITY_DEFAULT).build())
        }
    }
}