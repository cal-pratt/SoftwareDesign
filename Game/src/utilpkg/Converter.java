package utilpkg;

import java.nio.ByteBuffer;

import com.joml.vector.Vector3f;
import com.joml.vector.Vector4f;
import com.joml.matrix.Matrix3f;
import com.joml.matrix.Matrix4f;

public class Converter {
	public static ByteBuffer toByteBuffer(Vector3f v){
		return ByteBuffer.allocate(12).putFloat(v.x).putFloat(v.y).putFloat(v.z);
	}
	public static ByteBuffer toByteBuffer(Vector4f v){
		return ByteBuffer.allocate(16).putFloat(v.x).putFloat(v.y).putFloat(v.z).putFloat(v.w);
	}
	public static ByteBuffer toByteBuffer(Matrix3f m){
		ByteBuffer buf = ByteBuffer.allocate(36);
		m.store(buf.asFloatBuffer());
		return buf;
	}
	public static ByteBuffer toByteBuffer(Matrix4f m){
		ByteBuffer buf = ByteBuffer.allocate(64);
		m.store(buf.asFloatBuffer());
		return buf;
	}
}
