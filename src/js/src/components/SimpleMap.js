import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import { PointIcon } from './Icons';
import Marker from './Marker';

const SimpleMap = (props) => {

  // const {mark} = props;

  const { center, mark } = props;

  return (
    // Important! Always set the container height explicitly
    <div style={{ height: '200px', width: '100%' }}>
      {center ? <GoogleMapReact
        bootstrapURLKeys={{ key: "AIzaSyDgtAqGDPPn779SipPA5k7zgTIYJM36a-o" }}
        defaultCenter={center}
        defaultZoom={props.zoom}
      >
        {/* {mark ? <PointIcon {...mark} /> : <text {...mark}>marker</text>} */}
        <Marker {...mark} />

      </GoogleMapReact> : <React.Fragment />}
      {/* <GoogleMapReact
        bootstrapURLKeys={{ key: "AIzaSyDgtAqGDPPn779SipPA5k7zgTIYJM36a-o" }}
        defaultCenter={center}
        defaultZoom={props.zoom}
      >
        <PointIcon
          lat={mark.lat}
          lng={mark.lon}
        />
      </GoogleMapReact> */}
    </div>
  );
}

SimpleMap.defaultProps = {
  zoom: 13,
  // TODO: use user city
  center: {
    lat: 33.075815,
    lng: -97.080487
  }
}

export default SimpleMap;