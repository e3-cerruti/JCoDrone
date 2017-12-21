package org.jointheleague.jcodrone.receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.time.Instant.now;

public class Start1State extends State {
    static Logger log = LogManager.getLogger(Start1State.class);
    private boolean errorState = false;

    @Override
    public StateMap call(Receiver receiver, byte data) {
        if (data == 0x0A) {
            receiver.startTimer();
            receiver.setCrc16calculated(0);
            errorState = false;
            return StateMap.START2;
        } else {
            if (!errorState) {
                errorState = true; // swallow subsequent bad bytes
                log.error("Unexpected byte {} received while waiting for message.", data);
            } else if (log.isDebugEnabled()) {
                log.debug("Swallowed message byte {}.", data);
            }
            return StateMap.START1;
        }
    }
}
