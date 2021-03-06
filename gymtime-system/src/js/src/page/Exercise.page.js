import React, { useContext } from 'react';
import { Card, Carousel, List, Collapse, Affix } from 'antd';
import CardList from '../components/CardList';
import './Exercise.page.css';
import { InfoContext } from '../context/InfoContextProvider';
import { FaRegImage } from 'react-icons/fa';

const { Meta } = Card;
const { Panel } = Collapse;

function onChange(a, b, c) {
    console.log(a, b, c);
}

const ExercisePage = (props) => {

    const infoContext = useContext(InfoContext);

    const { state } = infoContext;
    const { exercises } = state;

    const carousel = (
        <Collapse accordion>
            <Panel header="This is panel header 1" key="1">
                <p></p>
            </Panel>
            <Panel header="This is panel header 2" key="2">
                <p></p>
            </Panel>
            <Panel header="This is panel header 3" key="3">
                <p></p>
            </Panel>
        </Collapse>
    );

    const getCategoryList = () => {
        if (exercises) {
            let categories = Object.keys(exercises);

            categories.sort();

            return categories.map((cat, i) => {
                return (
                    <Panel
                        header={cat.toUpperCase()}
                        key={cat}
                    >
                        <CardList
                            grid={{
                                gutter: 8,
                                xs: 1,
                                sm: 2,
                                md: 2,
                                lg: 3,
                                xl: 4,
                                xxl: 6,
                            }}
                            key={cat}
                            // header={<h1>{cat.toUpperCase()}</h1>}
                            cards={getExerciseCards(exercises[cat])} />
                    </Panel>
                );
            })
        }
    }

    const getExerciseCards = (exs) => {
        return exs.sort((a, b) => a.name.localeCompare(b.name))
            .map(ex => {
                return getExerciseCard(ex);
            })
    }

    const getExerciseCard = (ex) => {

        const { images } = ex;

        return (
            <Card
                key={ex.name}
                hoverable
                // style={{ width: 240 }}
                cover={images ? getCardCarouselCover(images) : <FaRegImage color={'grey'} size={70} />}
            >
                <Meta title={ex.name} description={ex.description} />
            </Card>
        );
    }

    const getCardCarouselCover = (images) => {
        return (
            <Carousel
                effect="scrollx"
            >
                {images.map((img, i) => {
                    return (
                        <img
                            key={img}
                            height="300"
                            src={img}
                            style={{ width: 240 }}
                        />
                    );
                })}
            </Carousel>
        );
    }

    return (
        <div className="exercise-page">
            <Collapse
                defaultActiveKey={0}
                accordion>
                {getCategoryList()}
            </Collapse>
        </div>
    )
}

export default ExercisePage;