/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Model.DataPackage;
import java.io.IOException;

/**
 *
 */
public interface inReceiveMessage {
    
    public void ReceiveMessage(DataPackage dataPackage) throws IOException;
}
