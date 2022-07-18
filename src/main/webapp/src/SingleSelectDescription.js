import "@patternfly/react-core/dist/styles/base.css";
import './fonts.css';

import React from 'react';
import {
    DataList,
    DataListCell,
    DataListItem,
    DataListItemCells,
    DataListItemRow,
    Divider,
    Grid,
    GridItem,
    Select,
    SelectGroup,
    SelectOption,
    SelectVariant
} from '@patternfly/react-core';


export class SingleSelectDescription extends React.Component {

        constructor(props) {

        super(props);

        this.state = {
            Items: [],
            DetailsLoaded: false,
            isOpen: false,
            selected: null,
            isDisabled: false,
        };

        this.onToggle = isOpen => {
            this.setState({
                isOpen
            });
        };

        this.onSelect = (event, selection, isPlaceholder) => {
            if (isPlaceholder) this.clearSelection();
            else {
                this.setState({
                    selected: selection,
                    isOpen: false,
                });
                console.log('selected:', selection);
            }
        };

        this.clearSelection = () => {
            this.setState({
                selected: null,
                isOpen: false
            });
        };
    }

    renderTypeElements(){
        if(this.state.selected != null && this.state.Items.filter(item => item.eventId === this.state.selected).map(item => item.payload.type) == 1) {
            return <DataList aria-label="Compact data list example" isCompact>
                <DataListItem aria-labelledby="simple-item1">
                    <DataListItemRow>
                        <DataListItemCells
                            dataListCells={[
                                <DataListCell width={1} key="primary content" >
                                    <span id="simple-item3"><b>Staking - Symbol</b></span>
                                </DataListCell>,
                                <DataListCell width={5} key="secondary content" >{this.state.Items.filter(item => item.eventId === this.state.selected).map(item => item.staking.symbol)}</DataListCell>
                            ]}
                        />
                    </DataListItemRow>
                </DataListItem>
                <DataListItem aria-labelledby="simple-item2">
                    <DataListItemRow>
                        <DataListItemCells
                            dataListCells={[
                                <DataListCell width={1} key="primary content" >
                                    <span id="simple-item3"><b>Staking - Staked</b></span>
                                </DataListCell>,
                                <DataListCell width={5} key="secondary content" >{this.state.Items.filter(item => item.eventId === this.state.selected).map(item => item.staking.staked)}</DataListCell>
                            ]}
                        />
                    </DataListItemRow>
                </DataListItem>
                <DataListItem aria-labelledby="simple-item3">
                    <DataListItemRow>
                        <DataListItemCells
                            dataListCells={[
                                <DataListCell width={1} key="primary content" >
                                    <span id="simple-item3"><b>Staking - rewarded</b></span>
                                </DataListCell>,
                                <DataListCell width={5} key="secondary content" >{this.state.Items.filter(item => item.eventId === this.state.selected).map(item => item.staking.rewarded)}</DataListCell>
                            ]}
                        />
                    </DataListItemRow>
                </DataListItem>
            </DataList>
        }

        if(this.state.selected != null && this.state.Items.filter(item => item.eventId === this.state.selected).map(item => item.payload.type) == 0) {
            return <DataList aria-label="Compact data list example" isCompact>
                <DataListItem aria-labelledby="simple-item1">
                    <DataListItemRow>
                        <DataListItemCells
                            dataListCells={[
                                <DataListCell width={1} key="primary content" >
                                    <span id="simple-item3"><b>Voting - Question</b></span>
                                </DataListCell>,
                                <DataListCell width={5} key="secondary content" >{this.state.Items.filter(item => item.eventId === this.state.selected).map(item => item.payload.questions[0].text)}</DataListCell>
                            ]}
                        />
                    </DataListItemRow>
                </DataListItem>

                {this.getAnswers().map((answer, index) =>(
                    <DataListItem aria-labelledby="simple-item1" key={index}>
                        <DataListItemRow>
                            <DataListItemCells
                                dataListCells={[
                                    <DataListCell width={1} key="primary content">
                                        <span id="simple-item3"><b>Voting - Answer {index + 1}</b></span>
                                    </DataListCell>,
                                    <DataListCell width={5} key="secondary content"> {answer.text} </DataListCell>
                                ]}
                            />
                        </DataListItemRow>
                    </DataListItem>
                ))}

            </DataList>
        }


        return null;
    }

    getAnswers(){
            const questions = this.state.Items.filter(item => item.eventId === this.state.selected).map(item => (item.payload.questions));
            const question = questions[0];
            return question[0].answers;
    }

    componentDidMount() {
        fetch(
            "http://localhost:8080/test")
            .then((res) => res.json())
            .then((json) => {
                this.setState({
                    Items: json,
                    DetailsLoaded: true
                });
                console.log('parsed json: ', json)
            })
    }



    render() {
        const { DetailsLoaded, Items} = this.state;
        if (!DetailsLoaded) return <div>
            <h1> Pleases wait some time.... </h1> </div> ;

        const { isOpen, selected, isDisabled, direction, isToggleIcon } = this.state;
        const titleId = 'select-descriptions-title';
        return (
            <div>
                <span id={titleId} hidden>
                    Title
                </span>
                <Grid hasGutter>
                    <GridItem span={3} offset={1}>
                        <Select
                            variant={SelectVariant.single}
                            placeholderText="Select an option"
                            aria-label="Select Input with descriptions"
                            onToggle={this.onToggle}
                            onSelect={this.onSelect}
                            selections={selected}
                            isOpen={isOpen}
                            aria-labelledby={titleId}
                            isDisabled={isDisabled}
                            isGrouped>
                            <SelectGroup label="Staking" key="group1">
                                {Items.filter(item => item.payload.type === 1).sort((a, b) => a.name.localeCompare(b.name)).map((item) => (
                                <SelectOption key={item.eventId} value={item.eventId}>{item.name}</SelectOption>
                                ))}
                            </SelectGroup>
                            <Divider key="divider" />
                            <SelectGroup label="Voting" key="group2">
                                {Items.filter(item => item.payload.type === 0).sort((a, b) => a.name.localeCompare(b.name)).map((item) => (
                                    <SelectOption key={item.eventId} value={item.eventId}>{item.name}</SelectOption>
                                ))}
                            </SelectGroup>

                        </Select>
                    </GridItem>
                    <GridItem span={12}>
                        <DataList aria-label="Compact data list example" isCompact>
                            <DataListItem aria-labelledby="simple-item1">
                                <DataListItemRow>
                                    <DataListItemCells
                                        dataListCells={[
                                            <DataListCell width={1} key="primary content">
                                                <span id="simple-item1"><b>Name</b></span></DataListCell>,
                                            <DataListCell width={5} key="secondary content" >{Items.filter(item => item.eventId === this.state.selected).map(item => item.name)}</DataListCell>
                                        ]}
                                    />
                                </DataListItemRow>
                            </DataListItem>
                            <DataListItem aria-labelledby="simple-item2">
                                <DataListItemRow>
                                    <DataListItemCells
                                        dataListCells={[
                                            <DataListCell width={1} key="primary content" >
                                                <span id="simple-item2"><b>Description</b></span>
                                            </DataListCell>,
                                            <DataListCell width={5} key="secondary content" >{Items.filter(item => item.eventId === this.state.selected).map(item => item.additionalInfo)}</DataListCell>
                                        ]}
                                    />
                                </DataListItemRow>
                            </DataListItem>
                            <DataListItem aria-labelledby="simple-item3">
                                <DataListItemRow>
                                    <DataListItemCells
                                        dataListCells={[
                                            <DataListCell width={1} key="primary content" >
                                                <span id="simple-item3"><b>Status</b></span>
                                            </DataListCell>,
                                            <DataListCell width={5} key="secondary content" >{Items.filter(item => item.eventId === this.state.selected).map(item => item.status)}</DataListCell>
                                        ]}
                                    />
                                </DataListItemRow>
                            </DataListItem>

                            { this.renderTypeElements() }

                        </DataList>
                    </GridItem>
                </Grid>
            </div>
        );
    }
}