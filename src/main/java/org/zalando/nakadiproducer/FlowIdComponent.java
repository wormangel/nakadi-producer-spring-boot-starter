package org.zalando.nakadiproducer;

import org.zalando.tracer.Tracer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlowIdComponent {
    
    private static final String X_FLOW_ID = "X-Flow-ID";
    
    private final Tracer tracer;
    
    public FlowIdComponent(Tracer tracer) {
        this.tracer = tracer;
    }

    public String getXFlowIdKey() {
        return X_FLOW_ID;
    }

    public String getXFlowIdValue() {
        if (tracer != null) {
            try {
                return tracer.get(X_FLOW_ID).getValue();
            } catch (IllegalArgumentException e) {
                log.warn("No trace was configured for the name {}. Returning null. " +
                        "To configure Tracer provide an application property: " +
                        "tracer.traces.X-Flow-ID=flow-id", X_FLOW_ID);
            } catch (IllegalStateException e) {
                log.warn("Unexpected Error while receiving the Trace Id {}. Returning null. " +
                    "Please check your tracer configuration: {}", X_FLOW_ID, e.getMessage());
            }
        } else {
            log.warn("No bean of class Tracer was found. Returning null.");
        }
        return null;
    }
}
