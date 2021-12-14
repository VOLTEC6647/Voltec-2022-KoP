// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.usfirst.frc6647.robot;

import org.usfirst.lib6647.json.JSONRobot;
import org.usfirst.lib6647.loops.Loop;
import org.usfirst.lib6647.loops.LoopType;
import org.usfirst.lib6647.oi.JController;
import org.usfirst.lib6647.subsystem.SuperSubsystem;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * Main {@link Robot} class, holds everything needed for the {@link Robot} to
 * run.
 */
public class Robot extends JSONRobot {
	/**
	 * The {@link Robot}'s {@link RobotContainer} instance, which contains all
	 * {@link Loop loops}, {@link SuperSubsystem subsystems}, and {@link JController
	 * joysticks}.
	 */
	private final RobotContainer container;

	/** Static {@link Robot} instance. */
	private static Robot instance = null;

	/**
	 * Method for getting currently running {@link Robot} instance.
	 * 
	 * @return The current {@link Robot} instance
	 */
	public static synchronized Robot getInstance() {
		if (instance == null)
			instance = new Robot();

		return instance;
	}

	/**
	 * Constructor for this implementation of {@link LooperRobot}, should only need
	 * to be created once, by the {@link Main} class.
	 */
	protected Robot() {
		super();

		container = new RobotContainer();

		container.initJoysticks();
		container.initSubsystems();
		container.configureButtonBindings();
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {
		// Start disabled loops, stop enabled, teleop, and auto.
		container.getLooper(LoopType.ENABLED).stop();
		container.getLooper(LoopType.TELEOP).stop();
		container.getLooper(LoopType.AUTO).stop();
		container.getLooper(LoopType.DISABLED).start();
	}

	@Override
	public void autonomousInit() {
		// Start enabled & auto loops, stop teleop & disabled.
		container.getLooper(LoopType.ENABLED).start();
		container.getLooper(LoopType.TELEOP).stop();
		container.getLooper(LoopType.AUTO).start();
		container.getLooper(LoopType.DISABLED).stop();
	}

	@Override
	public void teleopInit() {
		// Start enabled & teleop loops, stop auto & disabled.
		container.getLooper(LoopType.ENABLED).start();
		container.getLooper(LoopType.TELEOP).start();
		container.getLooper(LoopType.AUTO).stop();
		container.getLooper(LoopType.DISABLED).stop();
	}

	@Override
	public void testInit() {
		// Stop every loop.
		container.getLooper(LoopType.ENABLED).stop();
		container.getLooper(LoopType.TELEOP).stop();
		container.getLooper(LoopType.AUTO).stop();
		container.getLooper(LoopType.DISABLED).stop();
	}

	/**
	 * Get this {@link Robot}'s {@link RobotContainer}.
	 * 
	 * @return The {@link RobotContainer} used by this {@link Robot}.
	 */
	public RobotContainer getContainer() {
		return container;
	}
}
