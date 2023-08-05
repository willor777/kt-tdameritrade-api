import com.willor777.td_api.CREDS
import com.willor777.td_api.TdaApi
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    runBlocking {
        val td = TdaApi(CREDS)

        val chain = td.getOptionChain(
            "AAPL",
            nStrikes = -1,
        )

        val calls = chain?.calls!!

        for (c in calls){
            println(c.strikePrice)
            println(c.daysToExpiration)
        }
    }
}