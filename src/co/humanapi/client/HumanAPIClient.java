package co.humanapi.client;

import co.humanapi.client.entity.*;
import gumi.builders.UrlBuilder;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * HumanAPI client class
 */
public class HumanAPIClient {

    /** URL for accessing the API */
    public static final String apiRoot = "https://api.humanapi.co/v1";

    /** Logger instance */
    private static Logger logger = Logger.getLogger(HumanAPIClient.class.getName());

    /** Access token for current session */
    private String accessToken;

    /** Debug flag */
    private Boolean debug = false;

    /**
     * Constructor without params, access token will be taken
     * from HUMANAPI_ACCESS_TOKEN environment variable
     */
    public HumanAPIClient() throws HumanAPIException {
        String accessToken = System.getenv("HUMANAPI_ACCESS_TOKEN");
        if (accessToken == null || accessToken.isEmpty()) {
            throw new HumanAPIException("You must create non empty HUMANAPI_ACCESS_TOKEN environment variable");
        }
        this.accessToken = accessToken;
    }

    /**
     * Constructor, requires access token
     *
     * @param accessToken access token to be used in the session
     */
    public HumanAPIClient(String accessToken) throws HumanAPIException {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new HumanAPIException("You must provide non empty `accessToken` parameter");
        }
        this.accessToken = accessToken;
    }

    /**
     * Set the debug flag.
     * If true, then all request and response details will be
     * sent to the logger instance.
     *
     * @param debug new flag value
     */
    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    /** Get debug flag */
    public Boolean getDebug() {
        return debug;
    }

    /**
     * Writes out debug message to logger
     * @param message debug message
     */
    private void debug(String message) {
        if (debug) {
            logger.info(message);
        }
    }

    /**
     * Execute API GET request and return JSONObject result
     *
     * @param path path to API resource
     * @return result returned from server
     * @throws HumanAPIException
     */
    public JSONObject execute(String path) throws HumanAPIException {
        Map<String, Object> emptyParams = new HashMap<String, Object>();
        return this.execute(path, emptyParams);
    }

    /**
     * Execute API GET request and return JSONObject result
     *
     * @param path path to API resource
     * @param parameters extra parameters
     * @return result returned from server
     * @throws HumanAPIException
     */
    public JSONObject execute(String path, Map<String, Object> parameters) throws HumanAPIException {
        try {
            return new JSONObject(executeBase(path, parameters));
        } catch (JSONException e) {
            throw new HumanAPIException(e);
        }
    }

    /**
     * Execute API GET request and return JSONObject result
     *
     * @param path path to API resource
     * @return result returned from server
     * @throws HumanAPIException
     */
    public JSONArray executeForArray(String path) throws HumanAPIException {
        Map<String, Object> emptyParams = new HashMap<String, Object>();
        return this.executeForArray(path, emptyParams);
    }

    /**
     * Execute API GET request and return JSONArray result
     *
     * @param path path to API resource
     * @param parameters extra parameters
     * @return result returned from server
     * @throws HumanAPIException
     */
    public JSONArray executeForArray(String path, Map<String, Object> parameters) throws HumanAPIException {
        try {
            return new JSONArray(executeBase(path, parameters));
        } catch (JSONException e) {
            throw new HumanAPIException(e);
        }
    }

    /**
     * Execute API GET request and return string result
     *
     * @param path path to API resource
     * @param parameters extra parameters
     * @return result returned from server
     * @throws HumanAPIException
     */
    private String executeBase(String path, Map<String, Object> parameters) throws HumanAPIException {
        String url = apiRoot + path;
        logger.info(String.format("GET %s %s", url, parameters.toString()));
        try {
            // build url
            UrlBuilder builder = UrlBuilder.fromString(url);
            for (String name : parameters.keySet()) {
                builder = builder.addParameter(name, parameters.get(name).toString());
            }
            builder = builder.setParameter("access_token", this.accessToken);
            debug("complete URL = " + builder.toString());

            // send req
            Resty resty = new Resty();
            TextResource res = resty.text(builder.toString());
            debug("result = " + res.toString());

            // check res
            if (!res.status(200)) {
                logger.severe(String.format("Error response: [%d] %s", res.http().getResponseCode(), res.toString()));
                throw new HumanAPIException(String.format("Error response: [%d]", res.http().getResponseCode()));
            }
            logger.info("done, response [200]");
            return res.toString();
        } catch (IOException e) {
            throw new HumanAPIException(e);
        }
    }

    /** Builds HumanEntity */
    public HumanEntity humanEntity() {
        return new HumanEntity(this);
    }

    /** Builds ProfileEntity */
    public ProfileEntity profileEntity() {
        return new ProfileEntity(this);
    }

    /** Builds BloodGlucoseEntity */
    public BloodGlucoseEntity bloogGlucoseEntity() {
        return new BloodGlucoseEntity(this);
    }

    /** Builds BloodOxygenEntity */
    public BloodOxygenEntity bloogOxygenEntity() {
        return new BloodOxygenEntity(this);
    }

    /** Builds BloodPressureEntity */
    public BloodPressureEntity bloogPressureEntity() {
        return new BloodPressureEntity(this);
    }

    /** Builds BmiEntity */
    public BmiEntity bmiEntity() {
        return new BmiEntity(this);
    }

    /** Builds BodyFatEntity */
    public BodyFatEntity bodyFatEntity() {
        return new BodyFatEntity(this);
    }

    /** Builds HeartRateEntity */
    public HeartRateEntity heartRateEntity() {
        return new HeartRateEntity(this);
    }

    /** Builds HeightEntity */
    public HeightEntity heightEntity() {
        return new HeightEntity(this);
    }

    /** Builds WeightEntity */
    public WeightEntity weightEntity() {
        return new WeightEntity(this);
    }

    /** Builds ActivityEntity */
    public ActivityEntity activityEntity() {
        return new ActivityEntity(this);
    }

    /** Builds LocationEntity */
    public LocationEntity locationEntity() {
        return new LocationEntity(this);
    }

    /** Builds SleepEntity */
    public SleepEntity sleepEntity() {
        return new SleepEntity(this);
    }

    /** Builds GeneticTraitEntity */
    public GeneticTraitEntity geneticTraitEntity() {
        return new GeneticTraitEntity(this);
    }

    /**
     * Build AppUserEntity
     *
     * @param appId Application Id
     * @param appQueryKey Application query key
     */
    public AppUserEntity appUserEntity(String appId, String appQueryKey) {
        return new AppUserEntity(this, appId,appQueryKey);
    }
}