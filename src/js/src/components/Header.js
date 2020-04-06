import React from "react";
import { render } from "react-dom";

const headerStyle = {
  fontSize: "20px",
  color: "white",
  borderBotton: "1px solid #E7E7E7",
  textAlign: "center",
  padding: "10px",
  position: "fixed",
  left: "0",
  top: "0",
  height: "60px",
  width: "100%",
  backgroundColor: 'rgb(223,123,46)',
  zIndex: 100
};

const phantomStyle = {
  display: "block",
  padding: "20px",
  height: "60px",
  width: "100%",
};

const Header = ({ children }) => {
  return (
    <div className="header">
      <div style={phantomStyle} />
      <div style={headerStyle}>{children}</div>
    </div>
  );
}

export default Header