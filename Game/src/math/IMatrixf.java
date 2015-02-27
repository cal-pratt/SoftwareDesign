package math;

public interface IMatrixf {
	
	public float get(int index);
	public float get(int col, int row);
	
	public IVectorf getColumn(int index);
	public IVectorf getRow(int index);
	
	public float[] get();
	public IMatrixf set(float[] m);
	public IMatrixf set(IMatrixf m);
	public IMatrixf setZero();
	public IMatrixf setIdentity();
}
