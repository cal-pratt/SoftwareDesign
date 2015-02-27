package shaderpkg;

import java.nio.ByteBuffer;

public interface IProgram {
    public void  useProgram(int mode, int count, int type, ByteBuffer indicies);
}
