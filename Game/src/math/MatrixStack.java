package math;

public class MatrixStack {
	private Matrix4[] stack;
	private int currIdx;
	
	public MatrixStack() {
		stack = new Matrix4[10];
		clear();
	}
	
	public MatrixStack clear() {
		stack[0].clearToIdentity();
		currIdx = 0;
		return this;
	}
	
	public Matrix4 getTop() {
		return stack[currIdx];
	}
	
	public MatrixStack setTop(Matrix4 m) {
		stack[currIdx].set(m);
		return this;
	}
	
	public MatrixStack pushMatrix() {
		if(++currIdx == stack.length) {
			Matrix4[] tempstack = new Matrix4[stack.length * 10];
			for(int i = 0; i < stack.length; i++){
				tempstack[i] = stack[i];
			}
			stack = tempstack;
		}
		
		stack[currIdx] = new Matrix4(stack[currIdx - 1]);
		
		return this;
	}
	
	public MatrixStack popMatrix() {
		if(currIdx == 0)
			throw new IllegalStateException("Already at the topmost matrix.");
		
		currIdx--;
		return this;
	}
}
