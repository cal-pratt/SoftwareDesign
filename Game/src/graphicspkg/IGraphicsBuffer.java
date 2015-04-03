/** IGraphicsBuffer
 * An Interface to help define actions required by vertex array objects or buffers
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */
package graphicspkg;

public interface IGraphicsBuffer {
	public void delete();
    
    public void bind();
    
    public void draw();
}
