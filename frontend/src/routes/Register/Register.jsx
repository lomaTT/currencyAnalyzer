import React, {useState} from 'react';
import {Step, StepLabel, Stepper, TextField} from "@mui/material";
import "./Register.css"
import Button from "@mui/material/Button";

const steps = ['Input your name and surname', 'Input your credentials and date of birth']

const Register = () => {
    const [activeStep, setActiveStep] = useState(0);

    const handleNext = () => {

        setActiveStep((prevActiveStep) => prevActiveStep + 1);
    }

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    }


    return (
        <div className={"register-class"}>
            <Stepper activeStep={activeStep}>
                {steps.map((label, index) => {
                    const stepProps = {};
                    const labelProps = {};
                    return (
                        <Step key={label} {...stepProps}>
                            <StepLabel {...labelProps}>{label}</StepLabel>
                        </Step>
                    );
                })}
            </Stepper>

            <div className="input-group">
                <TextField
                    id="outlined-multiline-flexible"
                    label="Name"
                    defaultValue=""
                    help-value="string"
                />

                <TextField
                    id="outlined-multiline-flexible"
                    label="Surname"
                    defaultValue=""
                    help-value="string"
                    // error
                    // helperText="Incorrect entry."
                />
            </div>

            <div className="controls">
                <Button
                    color="inherit"
                    disabled={activeStep === 0}
                    onClick={handleBack}
                    sx={{mr: 1}}
                >
                    Back
                </Button>

                <Button
                    color="inherit"
                    disabled={activeStep === steps.length - 1}
                    onClick={handleNext}
                    sx={{mr: 1}}
                >
                    Next
                </Button>
            </div>

        </div>
    );
};

export default Register;