import React from 'react';
import { Masthead, MastheadToggle, MastheadMain, MastheadBrand, MastheadContent, Button } from '@patternfly/react-core';
import BarsIcon from '@patternfly/react-icons/dist/js/icons/bars-icon';

export const LightVariant = () => (
    <Masthead id="light-masthead" backgroundColor="light">
        <MastheadToggle>
            <Button variant="plain" onClick={() => {}} aria-label="Global navigation">
                <BarsIcon />
            </Button>
        </MastheadToggle>
        <MastheadMain>
            <MastheadBrand>Home</MastheadBrand>
        </MastheadMain>
        <MastheadContent>
            <span>Votes</span>
        </MastheadContent>
    </Masthead>
)