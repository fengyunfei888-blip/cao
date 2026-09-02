package com.cardev.deploy;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class DomainDetector {

    private final Set<String> observed = new LinkedHashSet<>();
    private volatile String latestDomain = "";
    private volatile String latestTargetDomain = "";

    public synchronized void record(String domain, boolean target) {
        if (domain == null) return;
        String clean = domain.trim().toLowerCase(Locale.US);
        if (clean.isEmpty()) return;

        observed.add(clean);
        latestDomain = clean;
        if (target) latestTargetDomain = clean;

        while (observed.size() > 100) {
            String first = observed.iterator().next();
            observed.remove(first);
        }
    }

    public String getLatestDomain() {
        return latestDomain;
    }

    public String getLatestTargetDomain() {
        return latestTargetDomain;
    }

    public synchronized String getObservedDomains() {
        StringBuilder sb = new StringBuilder();
        for (String d : observed) {
            sb.append(d).append('\n');
        }
        return sb.toString();
    }
}
