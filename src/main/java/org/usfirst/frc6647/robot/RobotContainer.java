// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.usfirst.frc6647.robot;

import java.util.logging.Logger;

import org.usfirst.lib6647.loops.Loop;
import org.usfirst.lib6647.loops.LoopContainer;
import org.usfirst.lib6647.oi.JController;
import org.usfirst.lib6647.subsystem.SuperSubsystem;

import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * A 'Container' class for the {@link Robot}, which contains all of the
 * {@link Robot}'s {@link Loop loops}, {@link SuperSubsystem subsystems}, and
 * {@link JController joysticks}.
 */
public class RobotContainer extends LoopContainer {
	// Declare SuperSubsystems to initialize.
	// e.g. 'private Chassis chassis;'

	@Override
	public void initSubsystems() {
		// Initialize every SuperSubsystem.
		// e.g. 'chassis = new Chassis();'

		// Register each initialized SuperSubsystem.
		// e.g. 'registerSubsystems(chassis, ...);'
	}

	@Override
	public void initJoysticks() {
		// Create JController object(s).
		// e.g. 'var driver1 = new JController(0);'
		var driver1 = new JController(0);

		// Run any extra JController initialization code.
		// e.g. 'driver1.setXY(Hand.kLeft, 0, 1);'
		//		'driver1.setXY(Hand.kRight, 2, 5);'
		Logger.getGlobal().info(() -> String.format("Found: '%s'!%n", driver1.getName()));

		if (driver1.getName().equals("Wireless Controller")) {
			driver1.setXY(Hand.kLeft, 0, 1);
			driver1.setXY(Hand.kRight, 2, 5);
		} else if (driver1.getName().toLowerCase().contains("xbox")
				|| driver1.getName().equals("Controller (Gamepad F310)")) {
			driver1.setXY(Hand.kLeft, 0, 1);
			driver1.setXY(Hand.kLeft, 4, 5);
		}
		// ...

		// Register each instantiated JController object in the joysticks HashMap.
		// e.g. 'registerJoystick(driver1, "driver1");'
		registerJoystick(driver1, "driver1");
	}

	@Override
	public void configureButtonBindings() {
		// Register any inline commands to buttons.
		// e.g. 'var driver1 = getJoystick("driver1");'
		// 		'var toggleReduction = new StartEndCommand(chassis::toggleReduction, chassis::toggleReduction);'
		// 		'driver1.get("L2", "LTrigger").whileHeld(toggleReduction);'
		// See: https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
	}
}