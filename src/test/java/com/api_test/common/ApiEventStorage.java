package com.api_test.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
public class ApiEventStorage extends BaseStep {
    /**
     * store respond for future verification
     */
    private String eventName;
    private JSONObject responseBody;
    private String re;
public ApiEventStorage(String ev) {
    this.eventName=ev;
    {
        try {
            System.out.println(eventName);
            this.responseBody = _ApiStep.verifyResponse(loadYAMLDocument(this.eventName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}

