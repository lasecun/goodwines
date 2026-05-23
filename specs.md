# Wine Diary App — Product Definition Document (PDD)

## Project Vision

Build a mobile-first application similar to Goodreads or Letterboxd, but focused on wine discovery, personal taste tracking, and social interaction.

The core idea is NOT to build another wine scanner like Vivino.

The goal is to create:

> “A personal and social memory system for wine lovers.”

The app should help users:
- remember wines they liked
- understand their personal taste
- discover new wines
- build collections and lists
- share experiences socially
- receive personalized recommendations

---

# Market Opportunity

## Existing Competitors

### Vivino
Strengths:
- massive database
- wine label scanner
- strong marketplace
- large community

Weaknesses:
- generic recommendations
- too commercial
- poor personalization
- weak emotional/social experience

### CellarTracker
Strengths:
- cellar management
- advanced wine tracking
- respected by enthusiasts

Weaknesses:
- outdated UX
- too technical
- not beginner friendly
- weak social layer

### Delectable
Strengths:
- social wine experience
- cleaner UX
- expert-driven content

Weaknesses:
- smaller community
- weak recommendation engine
- limited engagement loops

---

# Strategic Positioning

## Positioning Statement

“Goodreads for wine.”

NOT:
- another wine database
- another marketplace
- another rating app

BUT:
- a wine identity platform
- a taste memory system
- a social wine journal

---

# Target Users

## Primary Audience

Casual-premium wine lovers.

Characteristics:
- 28–45 years old
- interested in gastronomy and travel
- drink wine socially
- want to learn without complexity
- enjoy collecting experiences

---

# Core Product Principles

1. Simplicity first
2. Beautiful and modern UX
3. Taste personalization
4. Social identity
5. Emotional memory
6. Fast onboarding
7. Community-driven discovery

---

# MVP Features

## 1. Wine Label Scanner

### Description
Users can scan a wine bottle label using the camera.

### Requirements
- image recognition
- wine matching
- manual correction
- fast processing
- confidence scoring

### UX Goal
Open app → scan → save wine in less than 10 seconds.

---

# 2. Personal Wine Journal

## Description
Users can save wines they tasted.

## Fields
- wine name
- winery
- region
- grape varieties
- vintage
- personal rating
- tasting notes
- occasion
- food pairing
- location
- price paid
- date tasted
- would drink again (yes/no)
- photos

## UX Requirements
- frictionless
- optional advanced fields
- quick-save mode

---

# 3. Personal Taste Profile

## Goal
The app should build a dynamic taste profile for each user.

## Examples
- prefers dry wines
- likes mineral reds
- dislikes sweet wines
- prefers Rioja over Ribera
- enjoys medium-bodied wines

## Technical Ideas
- embeddings from tasting notes
- clustering users by taste
- collaborative filtering
- flavor tag weighting

---

# 4. Recommendation Engine

## Goal
Recommend wines users are likely to enjoy.

## Recommendation Sources
- similar users
- previous likes
- flavor profile similarity
- contextual matching

## Recommendation Examples
“If you liked X, try Y.”
“People with similar taste loved this.”
“You usually enjoy volcanic wines.”

---

# 5. Social Features

## Features
- follow users
- activity feed
- comments
- likes
- wine lists
- wishlist
- favorites
- profile pages

## Inspired By
- Goodreads
- Letterboxd
- Untappd

---

# 6. Statistics & Insights

## Examples
- favorite regions
- most consumed grapes
- average bottle price
- yearly recap
- wine map
- top wineries
- monthly trends

## Engagement Feature
“Wine Wrapped” yearly summary.

---

# Advanced Features (Post-MVP)

## AI Taste Assistant
Natural language wine recommendations.

Example:
“I want something similar to the Barolo I liked last month but cheaper.”

---

## Smart Pairings
Food + mood + context recommendations.

Examples:
- date night wine
- pizza wine
- summer terrace wine
- winter comfort wine

---

## Communities
- natural wine lovers
- Rioja fans
- Champagne collectors
- affordable wine groups

---

## Cellar Management
For advanced users:
- inventory
- bottle aging
- drinking windows
- storage tracking

---

# UX/UI Direction

## Design Principles
- modern
- emotional
- elegant
- premium but approachable
- highly visual
- dark mode support

## Inspirations
- Letterboxd
- Goodreads
- Untappd
- Airbnb
- Spotify

---

# Technical Architecture

## Frontend
Recommended:
- React Native OR Flutter
- TypeScript
- Expo (if React Native)

## Backend
Recommended:
- Node.js + NestJS
OR
- Supabase

## Database
PostgreSQL

## Search
- ElasticSearch OR Meilisearch

## AI/ML
- OpenAI embeddings
- recommendation engine
- vector search

## Cloud
- AWS
OR
- Google Cloud
OR
- Supabase managed infra

---

# Suggested Data Model

## User
- id
- username
- email
- avatar
- bio
- taste profile
- followers
- following

## Wine
- id
- winery
- name
- vintage
- region
- grapes
- tasting profile
- image

## TastingEntry
- id
- user_id
- wine_id
- rating
- notes
- date
- location
- pairing
- mood

## Review
- text
- likes
- comments

## List
- favorites
- wishlist
- regions
- custom collections

---

# Monetization

## Freemium Model

### Free
- wine journal
- scanning
- social features
- basic recommendations

### Premium
- advanced AI recommendations
- cellar management
- advanced statistics
- unlimited collections
- export tools
- premium profile customization

---

# Go-To-Market Strategy

## Initial Focus
Do NOT compete directly with Vivino.

The product should focus on:
- identity
- personalization
- social interaction
- memory
- discovery

NOT:
- marketplace
- generic ratings

---

# Growth Loops

## Organic Sharing
- wine cards
- yearly summaries
- lists
- tasting history

## Social Retention
- friends activity
- comments
- achievements

## AI Retention
- evolving taste profile
- personalized discoveries

---

# Product Risks

## Main Risks
- insufficient wine database
- weak recommendation quality
- low social activity at launch
- onboarding friction

## Mitigations
- integrate existing wine APIs initially
- prioritize UX over complexity
- seed community content
- optimize scanning flow

---

# Suggested MVP Roadmap

## Phase 1 — MVP
- authentication
- wine scanner
- journal
- ratings
- feed
- user profile

## Phase 2
- recommendations
- AI profile
- statistics
- lists

## Phase 3
- communities
- premium
- cellar management
- marketplace integrations

---

# Success Metrics

## Activation
- first wine scanned
- first journal entry

## Retention
- weekly active users
- wines logged per month

## Engagement
- social interactions
- recommendations opened
- lists created

## Monetization
- premium conversion
- retention of premium users

---

# Product Philosophy

The app should feel like:
- Spotify Wrapped for wine
- Goodreads for taste
- Letterboxd for wine culture

The emotional connection is more important than technical wine expertise.

The core differentiator is:
> “understanding the user’s palate better than anyone else.”
