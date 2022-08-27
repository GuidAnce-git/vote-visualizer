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

import {useEffect, useState} from "react";
import {Navigate, Route, Routes, useLocation} from "react-router-dom";
import {ThemeProvider} from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import Sidenav from "./examples/Sidenav";
import Configurator from "./examples/Configurator";
import theme from "./assets/theme";
import themeDark from "./assets/theme-dark";
import routes from "./routes";
import {setMiniSidenav, useMaterialUIController} from "./context";
import brandWhite from "./assets/images/logo-ct.png";
import brandDark from "./assets/images/logo-ct-dark.png";

export default function App() {
    const [controller, dispatch] = useMaterialUIController();
    const {
        miniSidenav,
        direction,
        layout,
        sidenavColor,
        transparentSidenav,
        whiteSidenav,
        darkMode,
    } = controller;
    const [onMouseEnter, setOnMouseEnter] = useState(false);
    const {pathname} = useLocation();


    // Open sidenav when mouse enter on mini sidenav
    const handleOnMouseEnter = () => {
        if (miniSidenav && !onMouseEnter) {
            setMiniSidenav(dispatch, false);
            setOnMouseEnter(true);
        }
    };

    // Close sidenav when mouse leave mini sidenav
    const handleOnMouseLeave = () => {
        if (onMouseEnter) {
            setMiniSidenav(dispatch, true);
            setOnMouseEnter(false);
        }
    };

    // Change the openConfigurator state
// Setting the dir attribute for the body element
    useEffect(() => {
        document.body.setAttribute("dir", direction);
    }, [direction]);

    // Setting page scroll to 0 when changing the route
    useEffect(() => {
        document.documentElement.scrollTop = 0;
        document.scrollingElement.scrollTop = 0;
    }, [pathname]);


    const getRoutes = (allRoutes) =>
        allRoutes.map((route) => {
            if (route.collapse) {
                return getRoutes(route.collapse);
            }

            if (route.route) {
                return <Route exact path={route.route} element={route.component} key={route.key}/>;
            }

            return null;
        });

    return (
        <ThemeProvider theme={darkMode ? themeDark : theme}>
            <CssBaseline/>
            {layout === "dashboard" && (
                <>
                    <Sidenav
                        color={sidenavColor}
                        brand={(transparentSidenav && !darkMode) || whiteSidenav ? brandDark : brandWhite}
                        brandName="IOTA Workbench"
                        routes={routes}
                        onMouseEnter={handleOnMouseEnter}
                        onMouseLeave={handleOnMouseLeave}
                    />
                </>
            )}
            {layout === "vr" && <Configurator/>}
            <Routes>
                {getRoutes(routes)}
                <Route path="*" element={<Navigate to="/stakingDashboard"/>}/>
            </Routes>
        </ThemeProvider>
    );
}