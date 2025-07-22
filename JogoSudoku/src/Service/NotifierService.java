package Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Service.EventEnum.CLEAR_SPACE;

public class NotifierService {

    private Map<EventEnum, List<EventListener>> listener = new HashMap<>(){{
        put(CLEAR_SPACE, new ArrayList<>());
    }};

    public void subscribe(final EventEnum eventType, EventListener Listener) {
        var selectedListener = listener.get(eventType);
        selectedListener.add(Listener);

    }

    public void notify(final EventEnum eventType) {
        listener.get(eventType).forEach(l -> l.update(eventType));
    }

}
