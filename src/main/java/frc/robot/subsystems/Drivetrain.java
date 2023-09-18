package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    // double exampleVariable = 27.33;

    double forwardSpeed, turnSpeed;
    double forwardSpeedMultiplier, turnSpeedMultiplier;
    double leftOutput, rightOutput;
    boolean boostEnabled, slowEnabled;
    CANSparkMax leftMotor, rightMotor;
    XboxController controller;

    /* Creates a new Drivetrain subsystem. */
    public Drivetrain() {
        forwardSpeed = turnSpeed = 0;
        
        forwardSpeedMultiplier = turnSpeedMultiplier = 0.3;
        
        leftOutput = rightOutput = 0;

        boostEnabled = slowEnabled = false;
        
        leftMotor = new CANSparkMax(11, MotorType.kBrushless);
        rightMotor = new CANSparkMax(12, MotorType.kBrushless);
        
        controller = new XboxController(0);
    }

    @Override
    public void periodic() {
        // Get forward and turn speed from controllers
        forwardSpeed = controller.getLeftY() * forwardSpeedMultiplier;
        turnSpeed = controller.getRightX() * turnSpeedMultiplier;

        // Calculate left and right motor speeds
        leftOutput = forwardSpeed + turnSpeed;
        rightOutput = forwardSpeed - turnSpeed;

        // Boost mode
        boostEnabled = controller.getXButtonPressed();

        if (boostEnabled) {
            leftOutput = leftOutput * 1.5;
            rightOutput = rightOutput * 1.5;
        }

        // Slow mode
        slowEnabled = controller.getYButtonPressed();
        leftOutput *= slowEnabled ? 0.5 : 1;
        rightOutput *= slowEnabled ? 0.5 : 1;

        // Set the motor outputs
        leftMotor.set(leftOutput);
        rightMotor.set(rightOutput);
    }
}
