package co.humanapi.client.entity;

import co.humanapi.client.HumanAPIClient;
import co.humanapi.client.HumanAPIException;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

import net.iharder.Base64;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Application user entity.
 */
public class AppUserEntity extends AbstractEntity {

    /** Logger instance */
    private static Logger logger = Logger.getLogger(HumanAPIClient.class.getName());

    /** Application Id */
    protected String appId;

    /** Application query key */
    protected String appQueryKey;

    /**
     * Build AppUserEntity
     *
     * @param client Core client object
     * @param appId Application Id
     * @param appQueryKey Application query key
     */
    public AppUserEntity(HumanAPIClient client, String appId, String appQueryKey) {
        super(client);
        this.appId = appId;
        this.appQueryKey = appQueryKey;
    }

    public JSONObject create(String externalId) throws HumanAPIException {
        String url = "/apps/" + appId + "/users";
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("externalId", externalId);
        try {
            return new JSONObject(executePost(url, data));
        } catch (JSONException e) {
            throw new HumanAPIException(e);
        }
    }

    public JSONObject get(String humanId) throws HumanAPIException {
        String url = "/apps/" + appId + "/users/" + humanId;
        try {
            return new JSONObject(executeGet(url));
        } catch (JSONException e) {
            throw new HumanAPIException(e);
        }
    }

    public JSONArray list() throws HumanAPIException {
        String url = "/apps/" + appId + "/users";
        try {
            return new JSONArray(executeGet(url));
        } catch (JSONException e) {
            throw new HumanAPIException(e);
        }
    }

    protected String executeGet(String url) throws HumanAPIException {
        String fullUrl = client.apiRoot + url;
        logger.info(String.format("GET %s", fullUrl));
        try {
            // send req
            TextResource res = getResty().text(fullUrl);
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

    protected String executePost(String url, Map<String, Object> data) throws HumanAPIException {
        String fullUrl = client.apiRoot + url;
        logger.info(String.format("POST %s: %s", fullUrl, data.toString()));
        try {
            // send req
            Resty resty = getResty();
            TextResource res = resty.text(fullUrl, resty.content(new JSONObject(data)));
            debug("result = " + res.toString());

            // check res
            if (!res.status(200) && !res.status(201)) {
                logger.severe(String.format("Error response: [%d] %s", res.http().getResponseCode(), res.toString()));
                throw new HumanAPIException(String.format("Error response: [%d]", res.http().getResponseCode()));
            }
            logger.info("done, response [200]");
            return res.toString();
        } catch (IOException e) {
            throw new HumanAPIException(e);
        }
    }

    /** Creates Resty instance with authorization header */
    private Resty getResty() {
        Resty resty = new Resty();
        String auth = appQueryKey + ":";
        resty.withHeader("Authorization", "Basic " + Base64.encodeBytes(auth.getBytes()));
        return resty;
    }

    /**
     * Writes out debug message to logger
     * @param message debug message
     */
    private void debug(String message) {
        if (client.getDebug()) {
            logger.info(message);
        }
    }
}
