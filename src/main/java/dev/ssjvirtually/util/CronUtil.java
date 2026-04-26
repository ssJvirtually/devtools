package dev.ssjvirtually.util;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public final class CronUtil {

    private CronUtil() {
    }

    public static String describe(String cronExpression, CronType type) {
        if (cronExpression == null || cronExpression.trim().isEmpty()) {
            return "";
        }

        try {
            CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(type);
            CronParser parser = new CronParser(cronDefinition);
            Cron cron = parser.parse(cronExpression);
            cron.validate();

            CronDescriptor descriptor = CronDescriptor.instance(Locale.ENGLISH);
            return descriptor.describe(cron);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cron expression: " + e.getMessage(), e);
        }
    }

    public static List<String> getNextExecutions(String cronExpression, CronType type, int count) {
        if (cronExpression == null || cronExpression.trim().isEmpty()) {
            return new ArrayList<>();
        }

        try {
            CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(type);
            CronParser parser = new CronParser(cronDefinition);
            Cron cron = parser.parse(cronExpression);
            cron.validate();

            ExecutionTime executionTime = ExecutionTime.forCron(cron);
            ZonedDateTime now = ZonedDateTime.now();
            List<String> nextExecutions = new ArrayList<>();

            ZonedDateTime next = now;
            for (int i = 0; i < count; i++) {
                Optional<ZonedDateTime> nextExec = executionTime.nextExecution(next);
                if (nextExec.isPresent()) {
                    next = nextExec.get();
                    nextExecutions.add(next.toString());
                } else {
                    break;
                }
            }
            return nextExecutions;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid cron expression: " + e.getMessage(), e);
        }
    }
}
