/*
 * Copyright (c) 2002-2007, Marc Prud'hommeaux. All rights reserved.
 *
 * This software is distributable under the BSD license. See the terms of the
 * BSD license in the documentation provided with this software.
 */
package jline;

import jline.console.ConsoleOperations;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Representation of the input terminal for a platform. Handles
 * any initialization that the platform may need to perform
 * in order to allow the {@link ConsoleReader} to correctly handle
 * input.
 *
 * @author <a href="mailto:mwp1@cornell.edu">Marc Prud'hommeaux</a>
 */
public abstract class Terminal
    implements ConsoleOperations
{
    /**
     * Returns true if the current console supports ANSI
     * codes.
     */
    public boolean isANSISupported() {
        return true;
    }

    /**
     * Read a single character from the input stream. This might
     * enable a terminal implementation to better handle nuances of
     * the console.
     */
    public int readCharacter(final InputStream in) throws IOException {
        return in.read();
    }

    /**
     * Reads a virtual key from the console. Typically, this will
     * just be the raw character that was entered, but in some cases,
     * multiple input keys will need to be translated into a single
     * virtual key.
     *
     * @param in the InputStream to read from
     * @return the virtual key
     */
    public int readVirtualKey(InputStream in) throws IOException {
        return readCharacter(in);
    }

    /**
     * Initialize any system settings
     * that are required for the console to be able to handle
     * input correctly, such as setting tabtop, buffered input, and
     * character echo.
     */
    public abstract void initializeTerminal() throws Exception;

    /**
     * Restore the original terminal configuration, which can be used when
     * shutting down the console reader. The ConsoleReader cannot be
     * used after calling this method.
     */
    public abstract void restoreTerminal() throws Exception;

    /**
     * Returns the current width of the terminal (in characters)
     */
    public abstract int getTerminalWidth();

    /**
     * Returns the current height of the terminal (in lines)
     */
    public abstract int getTerminalHeight();

    /**
     * Returns true if this terminal is capable of initializing the
     * terminal to use jline.
     */
    public abstract boolean isSupported();

    /**
     * Returns true if the terminal will echo all characters type.
     */
    public abstract boolean getEcho();

    /**
     * Invokes before the console reads a line with the prompt and mask.
     */
    public void beforeReadLine(ConsoleReader reader, String prompt,
                               Character mask)
    {
    }

    /**
     * Invokes after the console reads a line with the prompt and mask.
     */
    public void afterReadLine(ConsoleReader reader, String prompt,
                              Character mask)
    {
    }

    /**
     * Returns false if character echoing is disabled.
     */
    public abstract boolean isEchoEnabled();


    /**
     * Enable character echoing. This can be used to re-enable character
     * if the ConsoleReader is no longer being used.
     */
    public abstract void enableEcho();


    /**
     * Disable character echoing. This can be used to manually re-enable
     * character if the ConsoleReader has been disabled.
     */
    public abstract void disableEcho();

    public InputStream getDefaultBindings() {
        return Terminal.class.getResourceAsStream("keybindings.properties");
    }
}
