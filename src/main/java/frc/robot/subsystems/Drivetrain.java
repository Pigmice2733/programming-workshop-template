package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// This is a comment. Any lines of code starting with "//" are for writing notes
// about the code, and will be ignored when the code is executing

// This is a subsystem class. It corresponds to a specific part of the robot (this one if for the Drivetrain)
public class Drivetrain extends SubsystemBase {
    double exampleVariable = 27.33;

    double forwardSpeedMultiplier = 0.3;
    double turnSpeedMultiplier = 0.3;

    CANSparkMax leftMotor = new CANSparkMax(11, MotorType.kBrushless);
    CANSparkMax rightMotor = new CANSparkMax(12, MotorType.kBrushless);

    XboxController controller = new XboxController(0);

    public Drivetrain() {
        // This is the constructor. Any code in this function will be run only once
        // as soon as the robot is turned on

    }

    @Override
    public void periodic() {
        // Any code in this function will be run repeatedly while the robot is
        // enabled Similar to the "repeat forever" block in scratch or Update function
        // in Unity

        // Get forward and turn speed from controllers
        double forwardSpeed = controller.getLeftY() * forwardSpeedMultiplier;
        double turnSpeed = controller.getRightX() * turnSpeedMultiplier;

        // Calculate left and right motor speeds
        double leftOutput = forwardSpeed + turnSpeed;
        double rightOutput = forwardSpeed - turnSpeed;

        // Boost mode
        boolean boostEnabled = controller.getXButtonPressed();

        if (boostEnabled == true) {
            leftOutput = leftOutput * 1.5;
            rightOutput = rightOutput * 1.5;
        }

        // Slow mode
        boolean slowEnabled = controller.getYButtonPressed();

        if (slowEnabled == true) {
            leftOutput = leftOutput * 0.5;
            rightOutput = rightOutput * 0.5;
        }

        // Set the motor outputs
        leftMotor.set(leftOutput);
        rightMotor.set(rightOutput);
    }
}
