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
package sep.mvc;

/**
 * The Model is the most fundamental component in an MVC application - it
 * encapsulates the application's core data structures. It offers operations on
 * the encapsulated data, and regulates when and how these operations should be
 * performed. For example, the validity of certain operations may depend on,
 * and transform, the current state of the Model. The Model should be
 * independent of any particular
 * {@linkplain sep.mvc.AbstractController controller} or
 * {@linkplain sep.mvc.AbstractView view}.
 * <p>
 * This base Model definition is empty. This definition is used to specify that
 * a Model is bound to a controller when the latter is created. In some
 * scenarios, a Model may maintain a reference to the View via which updates
 * can be actively pushed when the model is operated on - we opt not to specify
 * such functionality for our present application.
 *
 * @author rhu1 {@literal <r.z.h.hu@herts.ac.uk>}
 */
public abstract class AbstractModel {

}
