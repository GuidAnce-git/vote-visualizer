import {useRowSelect} from '@table-library/react-table-library/select';
import {Table, Header, HeaderRow, HeaderCell, Body, Row, Cell,} from '@table-library/react-table-library/table';
import MDBox from "../../../../components/MDBox";
import MDTypography from "../../../../components/MDTypography";
import Card from "@mui/material/Card";
import React, {useState, useEffect} from "react";
import iotaAssembly from "../../../../assets/images/small-logos/iota_assembly-mark.png";
import MDBadge from "../../../../components/MDBadge";
import {getEvents} from "../../../../services/events";
import MDAvatar from "../../../../components/MDAvatar";
import Grid from "@mui/material/Grid";
import ComplexStatisticsCard from "../../../../examples/Cards/StatisticsCards/ComplexStatisticsCard";
import WeekendIcon from "@mui/icons-material/Weekend";
import LeaderboardIcon from "@mui/icons-material/Leaderboard";
import {getVotingEvents} from "../../../../services/votingEvents";


export default function VotingEvents() {

    const [events, setEvents] = useState([]);
    const [selectedItem, setSelectedItem] = useState([]);



    // load data from API
    useEffect(() => {
        let mounted = true;
        getVotingEvents()
            .then(items => {
                if (mounted) {
                    setEvents(items)
                }
            })
        return () => mounted = false;
    }, [])

    const Network = ({image, name}) => (
        <MDBox display="flex" alignItems="center" lineHeight={1}>
            <MDAvatar src={image} name={name} size="sm"/>
            <MDTypography variant="button" fontWeight="medium" ml={1} lineHeight={1}>
                {name}
            </MDTypography>
        </MDBox>
    );

    const rows = [];
    events.map((item, i) => (
        rows.push({
            id: item.eventId,
            name: <Network image={iotaAssembly} name={item.name}/>,
            type: (
                <MDTypography variant="caption" color="text" fontWeight="medium">
                    {item.payload.type === 1 ? "staking" : "voting"}
                </MDTypography>
            ),
            status: (
                <MDBox ml={-1}>
                    <MDBadge badgeContent={item.status} color={item.status !== "ended" ? "success" : "dark"}
                             variant="gradient" size="sm"/>
                </MDBox>
            )
        })
    ));

    const data = {nodes: rows};
    const select = useRowSelect(data, {
            onChange: onSelectChange,
        },
        {
            sCarryForward: false,
        });

    function onSelectChange(action, state) {
        setSelectedItem(events.find(item => item.eventId === state.id));
    }

    function smallStakingStatistics(){
        return (
            <Grid container spacing={3}>
                <Grid item xs={12} md={6} lg={3}>
                    <MDBox mb={1.5}>
                        <ComplexStatisticsCard
                            color="dark"
                            icon={<WeekendIcon fontSize='medium'/>}
                            title="Bookings"
                            count={281}
                            percentage={{
                                color: "success",
                                amount: "+55%",
                                label: "than lask week",
                            }}
                        />
                    </MDBox>
                </Grid>
                <Grid item xs={12} md={6} lg={3}>
                    <MDBox mb={1.5}>
                        <ComplexStatisticsCard
                            icon={<LeaderboardIcon fontSize='medium'/>}
                            title="Today's Users"
                            count="2,300"
                            percentage={{
                                color: "success",
                                amount: "+3%",
                                label: "than last month",
                            }}
                        />
                    </MDBox>
                </Grid>
                <Grid item xs={12} md={6} lg={3}>
                    <MDBox mb={1.5}>
                        <ComplexStatisticsCard
                            color="success"
                            icon="store"
                            title="Revenue"
                            count="34k"
                            percentage={{
                                color: "success",
                                amount: "+1%",
                                label: "than yesterday",
                            }}
                        />
                    </MDBox>
                </Grid>
                <Grid item xs={12} md={6} lg={3}>
                    <MDBox mb={1.5}>
                        <ComplexStatisticsCard
                            color="primary"
                            icon="person_add"
                            title="Followers"
                            count="+91"
                            percentage={{
                                color: "success",
                                amount: "",
                                label: "Just updated",
                            }}
                        />
                    </MDBox>
                </Grid>
            </Grid>
        )
    }

    function smallVotingStatistics(){

    }


    function smallStatistics(){
        return(
            <Grid container spacing={3}>
                <Grid item xs={12} md={6} lg={3}>
                    <MDBox mb={1.5}>
                        <ComplexStatisticsCard
                            color="dark"
                            icon={<WeekendIcon fontSize='medium'/>}
                            title="test"
                            count={123}
                            percentage={{
                                color: "success",
                                amount: "+55%",
                                label: "than lask week",
                            }}
                        />
                    </MDBox>
                </Grid>
                <Grid item xs={12} md={6} lg={3}>
                    <MDBox mb={1.5}>
                        <ComplexStatisticsCard
                            icon={<LeaderboardIcon fontSize='medium'/>}
                            title="Today's Users"
                            count="2,300"
                            percentage={{
                                color: "success",
                                amount: "+3%",
                                label: "than last month",
                            }}
                        />
                    </MDBox>
                </Grid>
                <Grid item xs={12} md={6} lg={3}>
                    <MDBox mb={1.5}>
                        <ComplexStatisticsCard
                            color="success"
                            icon="store"
                            title="Revenue"
                            count="34k"
                            percentage={{
                                color: "success",
                                amount: "+1%",
                                label: "than yesterday",
                            }}
                        />
                    </MDBox>
                </Grid>
                <Grid item xs={12} md={6} lg={3}>
                    <MDBox mb={1.5}>
                        <ComplexStatisticsCard
                            color="primary"
                            icon="person_add"
                            title="Followers"
                            count="+91"
                            percentage={{
                                color: "success",
                                amount: "",
                                label: "Just updated",
                            }}
                        />
                    </MDBox>
                </Grid>
            </Grid>
        )

    }



    return (
        <Grid item xs={12} md={6} lg={12}>
            <Card>
                <Grid item xs={12} md={6} lg={12}>
                    <MDBox display="flex" justifyContent="space-between" alignItems="center" p={3}>
                        <MDBox>
                            <MDTypography variant="h6" gutterBottom>
                                IOTA Events
                            </MDTypography>
                        </MDBox>
                    </MDBox>
                </Grid>
                <MDBox>
                    <Table data={data} select={select}>
                        {(tableList) => (
                            <>
                                <Header>
                                    <HeaderRow>
                                        <HeaderCell>Name</HeaderCell>
                                        <HeaderCell>Type</HeaderCell>
                                        <HeaderCell>Status</HeaderCell>
                                    </HeaderRow>
                                </Header>
                                <Body>
                                    {tableList.map((item) => (
                                        <Row key={item.id} item={item}>
                                            <Cell>{item.name}</Cell>
                                            <Cell>{item.type}</Cell>
                                            <Cell>{item.status}</Cell>
                                        </Row>
                                    ))}
                                </Body>
                            </>
                        )}
                    </Table>
                </MDBox>
            </Card>
            <MDBox mb={3} mt={4.5}>
                <Grid container spacing={3}>
                    <Grid item xs={12} md={7}>
                        <Card id="description">
                            <MDBox pt={3} px={2}>
                                <MDTypography variant="h6" fontWeight="medium">
                                    Description
                                </MDTypography>
                            </MDBox>
                            <MDBox pt={1} pb={2} px={2}>
                                <MDTypography variant="h6" fontWeight="light">
                                    {selectedItem.additionalInfo}
                                </MDTypography>
                            </MDBox>
                        </Card>
                    </Grid>
                    <Grid item xs={12} md={5}>
                        <Card id="information">
                            <MDBox pt={3} px={2}>
                                <MDTypography variant="h6" fontWeight="medium">
                                    Information
                                </MDTypography>
                            </MDBox>
                            <MDBox pt={1} pb={2} px={2}>
                                <MDTypography variant="h6" fontWeight="light">
                                    <table>
                                        <tr>
                                            <td>
                                                Created at:
                                            </td>
                                            <td>
                                                {selectedItem.createdAt}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                Last update:
                                            </td>
                                            <td>
                                                {selectedItem.updatedAt}
                                            </td>
                                        </tr>
                                    </table>
                                </MDTypography>
                            </MDBox>
                        </Card>
                    </Grid>
                </Grid>
            </MDBox>
        </Grid>

    );
}