import React, { useContext, useState, useEffect } from 'react';
import { TreeSelect } from 'antd';
import { InfoContext } from '../context/InfoContextProvider';

const { TreeNode } = TreeSelect;

const ExerciseSelect = ({ value, onChange, style }) => {

    const infoContext = useContext(InfoContext);
    const { state: infoState } = infoContext;
    const { exercises } = infoState;

    // console.log('exerciseSelect props value', value);

    const [selectExercise, setSelectExercise] = useState(value);

    useEffect(() => {
        if (onChange) {
            onChange(selectExercise);
        }
    }, [selectExercise])

    const selectHandler = (value) => {
        setSelectExercise(value);
    }

    const getExerciseOptions = () => {
        if (exercises) {
            const cats = Object.keys(exercises).sort();

            return cats.map((c, i) => {

                return (
                    <TreeNode key={c} value={c} selectable={false} title={c.toUpperCase()}>
                        {
                            exercises[c].sort((ex1, ex2) => ex1.name.localeCompare(ex2.name))
                                .map((ex, j) => {
                                    return (
                                        <TreeNode key={ex.name} value={ex} title={ex.name}
                                        />
                                    );
                                })
                        }
                    </TreeNode>);
            })
        }
    }

    return (
        <React.Fragment>
            <TreeSelect
                placeholder="Select Exercise"
                style={style || null}
                onChange={selectHandler}
                value={selectExercise}
            >
                {getExerciseOptions()}
            </TreeSelect>
        </React.Fragment>
    )
}

export default ExerciseSelect;