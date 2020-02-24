/*
 * This file is part of the base framework given to you for the 6COM1038 
 * Software Engineering Practice coursework assessment -- a text-based command
 * line tweeter application.
 * 
 * Generally, you do not need to, and should not, modify this file.  Any 
 * testing of your submitted coded will, in the first instance, be against the
 * base framework as provided.  
 * 
 * You may, however, wish to modify the base framework classes as part of your 
 * debugging or for your own interest -- that is fine, but you should ensure 
 * any such modifications are compatible with the existing base code, i.e., 
 * with regards to compilation and functionality.  You are advised to conduct 
 * your own tests against the base framework.
 */
/**
 * The server-side classes for you to stand up a minimal but functional Seeter
 * server and run your client against.
 * <p>
 * A <b>seet</b> is simply a pair of strings: the name (a single, non-empty
 * alphanumeric word of maximum length 8) of the <b>user</b> who wrote this
 * seet, and its <b>body</b> text (a single, non-empty line of maximum length
 * 48 characters). A Seeter server records an ordered list of seets per
 * <b>topic</b> (again, a non-empty alphanumeric word of maximum length 8), and
 * allows clients to publish and fetch seets for the client-specified topic.
 * <p>
 * An instance of {@link sep.seeter.server.Server} understands and communicates
 * messages as defined in {@link sep.seeter.net.message}.
 */
package sep.seeter.server;
