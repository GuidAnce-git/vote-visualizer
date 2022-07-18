/* eslint-disable react/prop-types */
/* eslint-disable react/function-component-definition */
/**
 =========================================================
 * Material Dashboard 2 React - v2.1.0
 =========================================================

 * Product Page: https://www.creative-tim.com/product/material-dashboard-react
 * Copyright 2022 Creative Tim (https://www.creative-tim.com)

 Coded by www.creative-tim.com

 =========================================================

 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 */
import MDBox from "../../../../../components/MDBox";
import MDTypography from "../../../../../components/MDTypography";
import MDAvatar from "../../../../../components/MDAvatar";
import iotaAssembly from "../../../../../assets/images/small-logos/iota_assembly-mark.png";
import React, {useState, useEffect} from "react";
import {getEvents} from "../../../../../services/events";
import MDBadge from "../../../../../components/MDBadge";
import MDButton from "../../../../../components/MDButton";
import Button from "@mui/material/Button";

export default function Data() {
    const [events, setEvents] = useState([]);

    // load data from API
    useEffect(() => {
        let mounted = true;
        getEvents()
            .then(items => {
                if(mounted) {
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

    const onRowClick = (state, rowInfo, column, instance) => {
        return {
            onClick: e => {
                console.log('A Td Element was clicked!')
                console.log('it produced this event:', e)
                console.log('It was in this column:', column)
                console.log('It was in this row:', rowInfo)
                console.log('It was in this table instance:', instance)
            }
        }
    }

    const columns = [];
    columns.push({Header: "Name", accessor: "name", align: "left"});
    columns.push({Header: "Type", accessor: "type", align: "center"});
    columns.push({Header: "Status", accessor: "status", align: "center"});

    const rows = [];
    events.map((item, i) => (
        rows.push({
            name: <Network image={iotaAssembly} name={item.name}/>,
            type: (
                <MDTypography variant="caption" color="text" fontWeight="medium" >
                    {item.payload.type === 1 ? "staking" : "voting" }
                </MDTypography>
            ),
            status: (
                <MDBox ml={-1}>
                    <MDBadge badgeContent={item.status} color={item.status !== "ended" ? "success" : "dark"} variant="gradient" size="sm" />
                </MDBox>
            )
        })
    ));

    return {columns, rows};
}
