package com.github.sbouclier;

import com.github.sbouclier.result.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Kraken API client
 *
 * @author Stéphane Bouclier
 */
public class KrakenAPIClient {

    public enum Interval {

        ONE_MINUTE(1),
        FIVE_MINUTES(5),
        FIFTEEN_MINUTES(15),
        THIRTY_MINUTES(30),
        ONE_HOUR(60),
        FOUR_HOURS(240),
        ONE_DAY(1440),
        ONE_WEEK(10080),
        FIFTEEN_DAYS(21600);

        private int minutes;

        Interval(int minutes) {
            this.minutes = minutes;
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "minutes=" + minutes +
                    '}';
        }
    }

    /**
     * Get server time
     *
     * @return server time
     * @throws IOException
     */
    public ServerTimeResult getServerTime() throws IOException {
        return new HttpApiClient<ServerTimeResult>()
                .callHttpClient("https://api.kraken.com/0/public/Time", ServerTimeResult.class);
    }

    /**
     * Get asset information
     *
     * @return asset information
     * @throws IOException
     */
    public AssetInformationResult getAssetInformation() throws IOException {
        return new HttpApiClient<AssetInformationResult>()
                .callHttpClient("https://api.kraken.com/0/public/Assets", AssetInformationResult.class);
    }

    /**
     * Get tradable asset pairs
     *
     * @return asset pairs
     * @throws IOException
     */
    public AssetPairsResult getAssetPairs() throws IOException {
        return new HttpApiClient<AssetPairsResult>()
                .callHttpClient("https://api.kraken.com/0/public/AssetPairs", AssetPairsResult.class);
    }

    /**
     * Get ticker information of pairs
     *
     * @param pairs list of pair
     * @return ticker information
     * @throws IOException
     */
    public TickerInformationResult getTickerInformation(List<String> pairs) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<>();
        params.put("pair", String.join(",", pairs));

        return new HttpApiClient<TickerInformationResult>()
                .callHttpClient("https://api.kraken.com/0/public/Ticker", TickerInformationResult.class, params);
    }


    /**
     * Get OHLC data
     *
     * @param pair     currency pair
     * @param interval interval of time
     * @param since data since givene id
     * @return data (OHLC + last id)
     * @throws IOException
     * @throws URISyntaxException
     */
    public OHLCResult getOHLC(String pair, Interval interval, Optional<Integer> since) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<>();
        params.put("pair", pair);
        params.put("interval", String.valueOf(interval.minutes));
        if(since.isPresent()) {
            params.put("since", String.valueOf(since.get()));
        }

        return new HttpApiClient<OHLCResult>()
                .callHttpClient("https://api.kraken.com/0/public/OHLC", OHLCResult.class, params);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        KrakenAPIClient client = new KrakenAPIClient();
        //ServerTimeResult result = client.getServerTime();
        //AssetInformationResult result = client.getAssetInformation();
        //AssetPairsResult result = client.getAssetPairs();
        //TickerInformationResult result = client.getTickerInformation(Arrays.asList("BTCEUR", "ETHEUR"));
        OHLCResult result = client.getOHLC("BTCEUR", Interval.ONE_MINUTE, Optional.empty());

        // System.out.println(result.getResult().get("XXBTZEUR"));
        System.out.println(result.getOHLCData());
        System.out.println(result.getLast());
        //System.out.println(result.getResult().get("XETHZEUR").ask + " > " + result.getResult().get("XETHZEUR").ask.getClass());
    }
}
