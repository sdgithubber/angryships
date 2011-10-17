package com.gemserk.games.angryships.templates;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gemserk.commons.artemis.components.MovementComponent;
import com.gemserk.commons.artemis.components.RenderableComponent;
import com.gemserk.commons.artemis.components.ScriptComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.artemis.events.EventManager;
import com.gemserk.commons.artemis.scripts.Script;
import com.gemserk.commons.artemis.scripts.ScriptJavaImpl;
import com.gemserk.commons.artemis.templates.EntityTemplateImpl;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.reflection.Injector;
import com.gemserk.games.angryships.components.ControllerComponent;
import com.gemserk.games.angryships.components.ExplosionComponent;
import com.gemserk.games.angryships.components.GameComponents;
import com.gemserk.games.angryships.components.PixmapCollidableComponent;
import com.gemserk.games.angryships.components.PixmapCollision;
import com.gemserk.games.angryships.entities.Events;
import com.gemserk.games.angryships.entities.Groups;
import com.gemserk.games.angryships.gamestates.Controller;
import com.gemserk.games.angryships.resources.GameResources;
import com.gemserk.games.angryships.scripts.MovementScript;
import com.gemserk.resources.ResourceManager;

public class BombTemplate extends EntityTemplateImpl {

	public static class PixmapCollidableScript extends ScriptJavaImpl {

		EventManager eventManager;

		@Override
		public void update(World world, Entity e) {
			PixmapCollidableComponent pixmapCollidableComponent = GameComponents.getPixmapCollidableComponent(e);
			PixmapCollision pixmapCollision = pixmapCollidableComponent.pixmapCollision;

			if (!pixmapCollision.isInContact())
				return;

			e.delete();

			eventManager.registerEvent(Events.explosion, e);
		}

	}

	ResourceManager<String> resourceManager;
	Injector injector;

	@Override
	public void apply(Entity entity) {

		Spatial spatial = parameters.get("spatial");
		Controller controller = parameters.get("controller");

		Sprite sprite = resourceManager.getResourceValue(GameResources.Sprites.BombSprite);

		entity.setGroup(Groups.Bombs);

		entity.addComponent(new RenderableComponent(0));
		entity.addComponent(new SpriteComponent(sprite, 0.5f, 0.5f, Color.WHITE));

		entity.addComponent(new SpatialComponent(spatial));
		// entity.addComponent(new PreviousStateSpatialComponent());

		entity.addComponent(new MovementComponent(150f, 0f, 0f));

		entity.addComponent(new ControllerComponent(controller));

		entity.addComponent(new PixmapCollidableComponent());
		entity.addComponent(new ExplosionComponent(injector.getInstance(BombExplosionAnimationTemplate.class), 32f));

		Script movementScript = injector.getInstance(MovementScript.class);
		Script pixmapCollidableScript = injector.getInstance(PixmapCollidableScript.class);

		entity.addComponent(new ScriptComponent(movementScript, pixmapCollidableScript));

	}

}