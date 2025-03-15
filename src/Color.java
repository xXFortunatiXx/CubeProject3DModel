public class Color {
    float r, g, b;
    public Color(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(char c){
        switch(c){
            case 'w':
                this.r = 1.0f;
                this.g = 1.0f;
                this.b = 1.0f;
                break;
            case 'b':
                this.r = 0.0f;
                this.g = 0.0f;
                this.b = 1.0f;
                break;
            case 'r':
                this.r = 1.0f;
                this.g = 0.0f;
                this.b = 0.0f;
                break;
            case 'g':
                this.r = 0.0f;
                this.g = 1.0f;
                this.b = 0.0f;
                break;
            case 'o':
                this.r = 1.0f;
                this.g = 0.6f;
                this.b = 0.0f;
                break;
            case 'y':
                this.r = 1.0f;
                this.g = 1.0f;
                this.b = 0.0f;
                break;
        }
    }
}
