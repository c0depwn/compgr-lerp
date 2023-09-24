package ch.fhnw.compgr;

public record Vector3(float x, float y, float z) {
    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    public static final Vector3 ONE = new Vector3(1, 1, 1);
    public static final Vector3 UNIT_X = new Vector3(1, 0, 0);
    public static final Vector3 UNIT_Y = new Vector3(0, 1, 0);
    public static final Vector3 UNIT_Z = new Vector3(0, 0, 1);

    public Vector3(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }

    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    public static Vector3 cross(Vector3 a, Vector3 b) {
        return new Vector3(
                a.y * b.z - a.z * b.y,
                a.z * b.x - a.x * b.z,
                a.x * b.y - a.y * b.x);
    }

    public static float distance(Vector3 a, Vector3 b) {
        return (float) Math.sqrt(distanceSquared(a, b));
    }

    public static float distanceSquared(Vector3 a, Vector3 b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) + (a.z - b.z) * (a.z - b.z);
    }

    public static float dot(Vector3 a, Vector3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public static Vector3 lerp(Vector3 v0, Vector3 v1, float t) {
        return new Vector3(
                v0.x * (1 - t) + v1.x * t,
                v0.y * (1 - t) + v1.y * t,
                v0.z * (1 - t) + v1.z * t);
    }

    public static Vector3 multiply(Vector3 a, Vector3 b) {
        return a.multiply(b);
    }

    public static Vector3 multiply(Vector3 a, float b) {
        return a.multiply(b);
    }

    public Vector3 multiply(Vector3 b) {
        return new Vector3(x * b.x, y * b.y, z * b.z);
    }

    public Vector3 multiply(float s) {
        return new Vector3(x * s, y * s, z * s);
    }

    public Vector3 negate() {
        return multiply(-1);
    }

    public static Vector3 normalize(Vector3 v) {
        var l = v.length();
        if (Math.abs(l) < 1e-6 || l == 1)
            return v;
        return v.multiply(1f / l);
    }

    public static Vector3 reflect(Vector3 vector, Vector3 normal) {
        return vector.subtract(normal.multiply(2 * dot(vector, normal)));
    }

    public Vector3 subtract(Vector3 v) {
        return subtract(this,v);
    }

    public static Vector3 subtract(Vector3 a, Vector3 b) {
        return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Vector3 transform(Vector3 position, Matrix4x4 m) {
        return new Vector3(
                (position.x * m.m11()) + (position.y * m.m21()) + (position.z * m.m31()) + m.m41(),
                (position.x * m.m12()) + (position.y * m.m22()) + (position.z * m.m32()) + m.m42(),
                (position.x * m.m13()) + (position.y * m.m23()) + (position.z * m.m33()) + m.m43()
        );
    }

    public static Vector3 transformNormal(Vector3 normal, Matrix4x4 matrix) {
        return new Vector3(
                (normal.x * matrix.m11()) + (normal.y * matrix.m21()) + (normal.z * matrix.m31()),
                (normal.x * matrix.m12()) + (normal.y * matrix.m22()) + (normal.z * matrix.m32()),
                (normal.x * matrix.m13()) + (normal.y * matrix.m23()) + (normal.z * matrix.m33())
        );
    }
}