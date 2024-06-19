package com.mygdx.game.Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

public class GameObject {
    protected Texture texture;
    public Body body;
    protected float width;
    protected float height;
    protected short cBit;

    GameObject(String texturePath, int x, int y, int width, int height, World world,short cBit) {
        this.width = width;
        this.height = height;
        this.cBit=cBit;

        texture = new Texture(texturePath);
        body = createBody(x, y, world);
    }
    public void hit() {}
    public void draw(SpriteBatch batch){
        batch.draw(texture,getX()-width/2f,getY()-height/2f,width,height);
    }
    public void move(Vector3 vector3){

    }

    private Body createBody(float x, float y, World world) {
        BodyDef def = new BodyDef(); // def - defenition (определение) это объект, который содержит все данные, необходимые для посторения тела

        def.type = BodyDef.BodyType.DynamicBody; // тип тела, который имеет массу и может быть подвинут под действием сил
        def.fixedRotation = true; // запрещаем телу вращаться вокруг своей оси
        Body body = world.createBody(def); // создаём в мире world объект по описанному нами определению

        CircleShape circleShape = new CircleShape(); // задаём коллайдер в форме круга
        circleShape.setRadius(Math.max(width, height) * GameSettings.SCALE / 2f); // определяем радиус круга коллайдера

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape; // устанавливаем коллайдер
        fixtureDef.density = 0.1f; // устанавливаем плотность тела
        fixtureDef.friction = 1f; // устанвливаем коэффициент трения
        fixtureDef.filter.categoryBits= (short) cBit;
        //fixtureDef.filter.maskBits = (short) (GameSettings.TRASH_BIT | GameSettings.BULLET_BIT | GameSettings.SHIP_BIT);

        Fixture fixture = body.createFixture(fixtureDef); // создаём fixture по описанному нами определению
        fixture.setUserData(this);
        circleShape.dispose(); // так как коллайдер уже скопирован в fixutre, то circleShape может быть отчищена, чтобы не забивать оперативную память.

        body.setTransform(x * GameSettings.SCALE, y * GameSettings.SCALE, 0); // устанавливаем позицию тела по координатным осям и угол поворота
        return body;
    }
    public int getX() {
        return (int) (body.getPosition().x / GameSettings.SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / GameSettings.SCALE);
    }

    public void setX(int x) {
        body.setTransform(x * GameSettings.SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * GameSettings.SCALE, 0);
    }
    public void dispose(){
        texture.dispose();
    }
}
