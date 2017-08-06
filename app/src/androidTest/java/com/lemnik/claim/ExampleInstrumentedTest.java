package com.lemnik.claim;

import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;

import com.lemnik.claim.util.ActionCommand;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void testSimpleCommand() throws Exception {
        final ParseIntCommand command = new ParseIntCommand();
        command.exec("1234");
        command.await();
    }

    @Test
    public void testSimpleError() throws Exception {
        final ParseIntCommand command = new ExpectFailureCommand();
        command.exec("hello world");
        command.await();

        final Exception error = command.capturedError;
        assertTrue(
                "Not a NumberFormatException: " + error.getClass().getName(),
                error instanceof NumberFormatException
        );
    }

    static class ParseIntCommand extends ActionCommand<String, Integer> {

        private final CountDownLatch latch = new CountDownLatch(1);

        Exception capturedError;

        @Override
        public Integer onBackground(final String value) throws Exception {
            assertNotSame(Looper.getMainLooper(), Looper.myLooper());
            return Integer.parseInt(value);
        }

        @Override
        public void onForeground(final Integer value) {
            assertNotNull(value);

            // make sure we are actually on the main thread
            assertSame(Looper.getMainLooper(), Looper.myLooper());

            latch.countDown();
        }

        @Override
        public void onError(final Exception error) {
            assertNotNull(error);

            // make sure we are actually on the main thread
            assertSame(Looper.getMainLooper(), Looper.myLooper());

            capturedError = error;

            latch.countDown();
        }

        void await() throws InterruptedException {
            latch.await();
        }

    }

    static class ExpectFailureCommand extends ParseIntCommand {
        @Override
        public void onForeground(final Integer value) {
            fail("onForeground should never be called");
        }
    }
}
