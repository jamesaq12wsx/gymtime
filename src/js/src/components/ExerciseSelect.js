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
            const cats = Object.keys(exercises);

            return cats.map((c, i) => {

                return (
                    <TreeNode key={c} value={c} selectable={false} title={c.toUpperCase()}>
                        {
                            exercises[c].map((ex, j) => {
                                return (
                                    <TreeNode key={ex.name} value={ex.name} title={ex.name}
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
                style={style || { width: 150 }}
                onChange={selectHandler}
                value={selectExercise}
            >
                {getExerciseOptions()}
            </TreeSelect>
        </React.Fragment>
    )
}

export default ExerciseSelect;